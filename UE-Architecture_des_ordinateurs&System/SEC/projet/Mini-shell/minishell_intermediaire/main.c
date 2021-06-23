#include <sys/types.h>
#include "init.h"
#include "parse.h"
#include "def.h"

char cmdline[MAXLINE+1];    //输入的命令行   Input command line
char avline[MAXLINE+1];     //处理输入的命令行，如去掉空格制表符，在参数后加'\0'
                            //Process the input command line, such as removing spaces and tabs, adding'\0' after the parameters
COMMAND cmd[PIPELINE];      //管道命令

char infile[MAXLINE+1];     //输入重定向 Input redirection
char outfile[MAXLINE+1];    //输出重定向 Output redirection

char *lineptr;              //指向cmdline printer
char *avptr;                //指向avline  printer

int cmd_count;              //命令的个数    number of commands
int backgnd;                //后台命令判断标志  Background command judgment flag
int append;                 //‘>>’判断标志   Judgment Mark of ‘>>’

int lastpid;                //等待最后一个管道进程推出  Wait for the last pipeline process to exit

char addr[80];         //当前工作目录 Current working directory

pid_t PID;
int main(void)
{
    setup();        //setup()   in init.c
    shell_loop();   //shell循环   shell loop in parse.c
    return 0;
}
