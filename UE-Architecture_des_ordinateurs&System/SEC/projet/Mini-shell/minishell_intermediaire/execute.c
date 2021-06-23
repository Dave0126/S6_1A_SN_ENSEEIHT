#include <sys/types.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <errno.h>
#include <string.h>
#include <signal.h>
#include <malloc.h>

#include "execute.h"
#include "extern.h"
#include  <unistd.h>


void forkexec(int i);

int execute_disk_command(void)
{
    if(cmd_count == 0)
        return 0;

    if(infile[0] != '\0')
        cmd[0].infd = open(infile, O_RDONLY);

    if(outfile[0] != '\0') {
        if(append) {
            cmd[cmd_count-1].outfd = open(outfile, O_WRONLY | O_CREAT
                    | O_APPEND, 0666);
        } else {
            cmd[cmd_count-1].outfd = open(outfile, O_WRONLY | O_CREAT
                    | O_TRUNC, 0666);
        }
    }

    /* 后台作业不会调用wait等待子进程推出
     * 为避免僵尸进程，可以忽略SIGCHLD信号
     * Background jobs will not call wait to wait for the child process to exit.
     * To avoid zombie processes, you can ignore the SIGCHLD signal
     */
    if(backgnd == 1) {
        signal(SIGCHLD, SIG_IGN);
        signal(SIGTSTP, SIG_DFL);
    } else {
        signal(SIGCHLD, SIG_DFL);
        signal(SIGTSTP, SIG_DFL);
    }

    int i;
    int fd;
    int fds[2];
    for(i=0; i<cmd_count; i++) {
        /* If it is not the last command, you need to create a pipeline */
        if(i < cmd_count-1) {
            pipe(fds);
            cmd[i].outfd = fds[1];
            cmd[i+1].infd = fds[0];
        }

        forkexec(i);

        if((fd=cmd[i].infd) != 0)
            close(fd);

        if((fd=cmd[i].outfd) != 1)
            close(fd);

    }

    if(backgnd == 0) {
        /* Foreground job, need to wait for the last process of the pipeline to exit  */
        while(wait(NULL) != lastpid)
        ;
    }
    return 0;
}

void forkexec(int i)
{
    pid_t fpid;
    fpid = fork();

    if(fpid < 0) {
        ERR_EXIT("fork");
    }
    else if(fpid == 0) {
        /* Child process  */
        //signal(SIGTSTP, ctrl_z);

        /* backgnd==1时将第一条命令的infd重定向至/dev/null */
        /* 当第一条命令试图从标准输入获取数据时立即返回EOF */

        if(i ==0) {
            /* 将第一个命令进程作为进程组组长 */
            setpgid(0, 0);
        }


        if(cmd[i].infd != 0) {
            close(0);
            dup(cmd[i].infd);
        }

        if(cmd[i].outfd != 1) {
            close(1);
            dup(cmd[i].outfd);
        }

        int j;
        for(j=3; j<sysconf(_SC_OPEN_MAX); j++) {
            close(j);
        }

        /* The foreground job can accept SIGINT and SIGQUIT signals
         * These two signals need to be restored to the default operation
         */
        if(backgnd == 0) {
            signal(SIGINT, SIG_DFL);
            signal(SIGQUIT, SIG_DFL);
            signal(SIGTSTP, SIG_DFL);
            if(execvp(cmd[i].args[0], cmd[i].args) < 0 )
            err_sys("command not found");
        }

        exit(EXIT_FAILURE);
    } else {
        /* Parent process  */
        lastpid = fpid;
        if(backgnd == 1) {
            //wait(NULL);
            printf("%d\n", fpid);
        } else {
        PID = fpid;
		//waitpid(fpid,NULL,WUNTRACED);
        //signal(SIGTSTP, ctrl_z);
        }
    }
}
