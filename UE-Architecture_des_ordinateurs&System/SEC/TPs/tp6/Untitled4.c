#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define NB_FILS 2     /* nombre de fils */

int main(int argc,char* argv[])
{
    int pipefd[3][2];
    char* grep_txt = argv[1];

    for (int i = 0; i < 3; ++i) {
        if (i <= NB_FILS) {
            int ret = pipe(pipefd[i+1]);
            if (ret == -1) {
                perror("PIPE ERROR!\n");
                exit(1);
            }
        }
        int child_id = fork();
        if (child_id < 0) {
            perror("FORK ERROR!\n");
        }
    }

    if (pipe(pipefd[1]) == 0 && pipe(pipefd[2] == 0)) {
        if (fork() == 0) {
            close(1);
            dup2(pipefd[1][1], 1);//标准输出流挂到管道写入端
            close(pipefd[1][0]);
            close(pipefd[2][0]);
            close(pipefd[2][1]);
            execlp("who", "who", NULL, NULL);
            close(pipefd[1][1]);
            exit(EXIT_SUCCESS);
        } else if (fork() == 0) {
            dup2(pipefd[1][0], 0);
            dup2(pipefd[2][1], 1);
            close(pipefd[1][1]);
            close(pipefd[2][0]);
            execlp("grep", "grep", grep_txt, NULL);
            close(pipefd[1][0]);
            close(pipefd[2][1]);
            exit(EXIT_SUCCESS);
        } else {
            close(0);
            dup2(pipefd[2][0], 0);//标准输入流挂到管道读取端
            close(pipefd[2][1]);
            close(pipefd[1][0]);
            close(pipefd[1][1]);
            execlp("wc", "wc", NULL , NULL);
            close(pipefd[2][0]);

        }
    return 0;
    }
}
