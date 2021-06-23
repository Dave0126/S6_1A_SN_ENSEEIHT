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
        ret = scanf("%s", buf);
        do_parse(buf);
        do_execute();
    }
    return 0;
}

void do_parse(char* buf)
{
    int i;
    int status = 0 ;
    for(argc = i = 0; buf[i]; i++)
    {
        if(!isspace(buf[i]) && status == 0)
        {
            argv[argc] = buf+i;
            argc++;
            status = 1;
        }
        else if(isspace(buf[i]))
        {
            status = 0;
            buf[i] = 0;
        }
    }
    argv[argc] = NULL;
}

void do_execute(void)
{
    int retFork;
    retFork = fork();
    switch(retFork)
    {
        case -1:
            perror("fork");
            exit(EXIT_FAILURE);
            break;
        case 0:
            execvp(argv[0], argv);
            perror("execvp");
            exit(EXIT_FAILURE);
        default:
        {
                int st;
                while(wait(&st) != retFork);
        }
    }
}
