#include <stdio.h>
#include  <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <fcntl.h>

#include "parse.h"
#include "init.h"
#include "extern.h"
#include "execute.h"
#include "builtin.h"

void get_name(char *name);
void printf_command();

/*
 * shell主循环
 */
void shell_loop(void)
{
	while (1) {
        /* 初始化环境 Initialization */
        init();
        /* 获取命令 */
		if (read_command() == -1)
			break;
        /* 解析命令 */
		parse_command();
        //printf_command();
        /* 执行命令 */
		execute_command();
	}

	printf("\n------exit-----\n");
}

/*
 * 读取命令 Read command
 * 成功返回0，失败或者读取到文件结束符(EOF)返回-1
 * Return 0 on success
 * Return -1 on failure or EOF
 */
int read_command(void)
{
    /* 按行读取命令，cmdline中包含'\n'字符
     * Read the command line by line
     * It contains the character'\n'
     */
	if (fgets(cmdline, MAXLINE, stdin) == NULL)
		return -1;
	return 0;
}

/*
 * 解析命令
 * Parsing command
 * Success -> return: the number of parsed commands
 * Failure -> return: -1
 */
int parse_command(void)
{
    /* 如果第一条命令为回车，直接返回
     *If the first command is Enter
     *Return 0
     */
    if(check("\n"))
        return 0;

    /* 判断是否内部命令,是则执行
     * Determine whether it is an internal command,
     * If it is, just execute it
     * Internal command : "cd" "exit" "jobs" "stop" "pg" "fg"
     */
    if(builtin())
        return 0;


    /* 1.Parse the first command  */
    get_command(0);

    /* 2.Determine whether there is input redirection  */
    if(check("<"))
        get_name(infile);

    /* 3.Determine if there is a pipe  */
    int i;
    for(i=1; i<PIPELINE; i++) {
        if(check("|"))
            get_command(i);
        else
            break;
    }

    /* 4.Determine whether there is output redirection */
    if(check(">")) {
        if(check(">")) {
            append = 1;
        }
        get_name(outfile);
    }

    /* 5.Determine whether a background job  */
    if(check("&"))
        backgnd = 1;

    /* 6.Determine whether the command is over '\n' */
    if(check("\n")) {
        cmd_count = i;
        return cmd_count;
    } else {
        fprintf(stderr, "Command line syntax error!\n");
        return -1;
    }

	return 0;
}

/*
 * 执行命令
 * 成功返回0，失败返回-1
 */
int execute_command(void)
{
    execute_disk_command();
   	return 0;
}


/*
 * 解析命令至cmd[i]
 * 提取cmdline中的命令参数至avline数组
 * 将COMMAND结构中的args[]中的每个指针指向这些参数
 * Parse the command to cmd[i]
 * Extract command parameters in cmdline to avline array
 * Point each pointer in args[] in the COMMAND structure to these parameters
 */
void get_command(int i)
{

    int j = 0;
    int inword;
    while(*lineptr != '\0') {
        /* Remove space and tab"\t"*/
        while(*lineptr == ' ' || *lineptr == '\t')
            lineptr++;

    /* Point the command i and parameter j to avptr  */
        cmd[i].args[j] = avptr;

        /* 提取参数
         * Extract parameters
         */
        while(*lineptr != '\0'
           && *lineptr != ' '
           && *lineptr != '\t'
           && *lineptr != '\n'
           && *lineptr != '<'
           && *lineptr != '>'
           && *lineptr != '|'
           && *lineptr != '&') {
            /* Extract to the array avline pointed by avptr  */
            *avptr++ = *lineptr++;
            inword = 1;
        }
        *avptr++ = '\0';

        switch(*lineptr) {
            case ' ':
            case '\t':
                j++;    // Next parameter
                inword = 0;
                break;

            case '\n':
            case '<':
            case '>':
            case '|':
            case '&':
                if(inword == 0) {
                    cmd[i].args[j] = NULL;
                }
                return;

            default:    //for '\0'
                return;
        }
    }

}

/*
 * 将lineptr指向的字符串与str进行匹配
 * 成功返回1，lineptr移过匹配的字符串
 * 失败返回0，lineptr保持不变
 * Match the string pointed to by lineptr with str
 * Return 1 successfully, the lineptr has moved over the matched string
 * Return 0 on failure, lineptr remains unchanged
 */
int check(const char *str)
{
    char *p;
    while(*lineptr == ' ' || *lineptr == '\t')
        lineptr++;

    p=lineptr;
    while(*str != '\0' && *str == *p) {
        str++;
        p++;
    }

    if(*str == '\0') {
        lineptr = p;    //lineptr移过所匹配的字符串
        return 1;
    }
    /* lineptr保持不变 */
    return 0;

}
void get_name(char *name)
{
    while(*lineptr == ' ' || *lineptr == '\t')
        lineptr++;

        while(*lineptr != '\0'
           && *lineptr != ' '
           && *lineptr != '\t'
           && *lineptr != '\n'
           && *lineptr != '<'
           && *lineptr != '>'
           && *lineptr != '|'
           && *lineptr != '&') {
            *name++ = *lineptr++;
        }
        *name = '\0';
}

void printf_command()
{
    int i;
    int j;
    printf("cmd_count = %d\n", cmd_count);

    if(infile[0] != '\0')
        printf("infile = [%s]\n", infile);

    if(outfile[0] != '\0')
        printf("outfile = [%s]\n", outfile);

    for(i=0; i<cmd_count; i++) {
        j=0;
        while(cmd[i].args[j] != NULL) {
            printf("[%s] ",cmd[i].args[j]);
            j++;
        }
        printf("\n");
    }
}
