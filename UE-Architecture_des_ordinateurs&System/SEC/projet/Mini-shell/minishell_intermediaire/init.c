#include <stdio.h>
#include <signal.h>
#include <string.h>
#include <unistd.h>

#include "init.h"
#include "extern.h"

void sigint_handler(int sig);

void setup(void)
{
    signal(SIGINT, sigint_handler);
    signal(SIGQUIT, SIG_IGN);
    signal(SIGTSTP, SIG_DFL);
}

void sigint_handler(int sig)
{
    getcwd(addr,sizeof(addr));  //获取当前路径
    //char *p;
    //p = getcwd(NULL,80);
    printf("[Guohao@ %s]$ ", addr);
    //free(p);
    fflush(stdout);
}

void init(void)
{
    memset(cmd, 0, sizeof(cmd));
    int i;
    for(i=0; i<PIPELINE; i++){
        cmd[i].outfd = 1;
        cmd[i].infd = 0;
    }
    memset(cmdline, 0, sizeof(cmdline));
    memset(avline, 0, sizeof(avline));
    memset(infile, 0, sizeof(infile));
    memset(outfile, 0, sizeof(outfile));

    lineptr = cmdline;
    avptr = avline;

    cmd_count = 0;
    backgnd = 0;
    append = 0;

    //char *p;
    //p = getcwd(NULL,80);
    getcwd(addr,sizeof(addr));  //获取当前路径
	printf("[Guohao@ %s]$ ", addr);
    //free(p);
	fflush(stdout);
}


