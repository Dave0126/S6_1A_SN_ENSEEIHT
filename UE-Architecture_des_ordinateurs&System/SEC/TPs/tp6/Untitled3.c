#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc,char* argv[])
{
    int pipefd[3][2];
    char* grep_txt = argv[1];

    if (pipe(pipefd[1]) == 0 && pipe(pipefd[2] == 0)) {
        if (fork() == 0) {
            //close(1);
            close(pipefd[1][0]);
            dup2(pipefd[1][1], STDOUT_FILENO);//标准输出流挂到管道写入端

            //close(pipefd[2][0]);
            //close(pipefd[2][1]);
            execlp("who", "who", NULL, NULL);
            //close(pipefd[1][1]);
            //exit(EXIT_SUCCESS);
        } else if (fork() == 0) {
            close(pipefd[1][1]);
            dup2(pipefd[1][0], STDIN_FILENO);

            close(pipefd[2][0]);
            dup2(pipefd[2][1], STDOUT_FILENO);

            execlp("grep", "grep", grep_txt, NULL);
            //close(pipefd[1][0]);
            //close(pipefd[2][1]);
            //exit(EXIT_SUCCESS);
        } else {
            //close(0);
            close(pipefd[1][0]);
            close(pipefd[1][1]);
            close(pipefd[2][1]);

            dup2(pipefd[2][0], STDIN_FILENO);//标准输入流挂到管道读取端

            execlp("wc", "wc", NULL , NULL);
            //close(pipefd[2][0]);

        }
    return 0;
    }
}
