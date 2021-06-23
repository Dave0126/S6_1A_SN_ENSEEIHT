/**
 * A minishell by language C.
 * @ author Guohao DAI | Groupe E | 1A | SN | ENSEEIHT
 * @ version 1.0
 */
#include <unistd.h> // fork
#include <sys/wait.h> // wait
#include <stdio.h>
#include <stdlib.h> // EXIT_FAILURE
#include <string.h>

char* argv[32];
int argc = 0;

int main()
{
    int ret;
    char buf[30] = {};
    while(1)
    {
        printf("[Guohao@localhost ~]$ ");
        ret = scanf("%s", buf); // To get the commend.
		//1.读取输入 ls -a
		//需要解决的问题: 空格不截断输入
		//scanf返回值:成功:赋值数量; 失败:EOF 
		//printf("cmd:[%s]\n",buf);
        parseCMD(buf); // To parse the command
        executeProc(); // To change the parent-child process
    }
    return 0;
}

void parseCMD(char* buf)
{
    int i;
    int status = 0 ;
    for(argc = i = 0; buf[i]; i++)
    {
        if(!isspace(buf[i]) && status == 0)
        {
        	//To store a command
            argv[argc] = buf+i;
            argc++;
            status = 1;
        }
        else if(isspace(buf[i]))
        {
            status = 0;
            buf[i] = 0;
        }
        //printf("argv[%d]--%s\n",i,argv[i]);
    }
    argv[argc] = NULL;
}

void executeProc(void)
{
    int retFork;
    retFork = fork(); // To create a child process.
    switch(retFork)
    {
        case -1:
            perror("fork");
            exit(EXIT_FAILURE);
            break;
        case 0:
        	//printf("I am the child process.\n");
            execvp(argv[0], argv);
            perror("execvp");
            exit(EXIT_FAILURE);
        default:
        {
                int st;
                while(wait(&st) != retFork);
                // The parent process waits for the child process to exit
        }
    }
}
