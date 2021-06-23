/**
 * Cours: Systèmes d'exploitation centralisés
 * TP: Redirections et tubes - Exercice 4
 * Realiser la ligne de commande " who | grep nom_utilisateur | wc -l "
 * Author: Guohao DAI / Group E / 1A / SN / ENSEEIHT
 * Date de la dernière modification: 12/05/2021
 * Résultat de Test automatisé: OK
 */

#include <stdio.h>          /* entrées sorties */
#include <unistd.h>         /* pimitives de base : fork, ...*/
#include <stdlib.h>         /* exit */
#include <sys/wait.h>       /* wait */
#include <string.h>         /* opérations sur les chaines */

#define NB_FILS 3     /* nombre de fils */

int pipe_fd[2][2];
int main(int argc,char* argv[])
{
    char* grep_txt = argv[1];
    int i = 0;
    for(i = 0; i < NB_FILS; ++i)
    {
        // Generation de PIPE
        if(i <= 2)
        {
            int ret = pipe(pipe_fd[i]);
            if(ret < 0)
            {
                perror("PIPE ERROR!\n");
                exit(1);
            }
        }
        int child_id = fork();
        if(child_id == 0)
            break;
    }

    /* the first child process */
    if(i == 0)
    {
        //printf("Processus de pid %d\n",getpid());
        //sleep(2);
        close(pipe_fd[i][0]);
        dup2(pipe_fd[i][1], STDOUT_FILENO);
        execlp("who", "who", NULL, NULL);
    }

    /* the second child process */
    else if(i == 1)
    {
        //printf("Processus de pid %d\n",getpid());
        close(pipe_fd[0][1]);
        dup2(pipe_fd[0][0], STDIN_FILENO);

        close(pipe_fd[1][0 ]);
        dup2(pipe_fd[1][1], STDOUT_FILENO);

        execlp("grep", "grep", grep_txt, NULL);
    }

    /* the third child process */
    else if(i == 2)
    {
        //printf("Processus de pid %d\n",getpid());
        close(pipe_fd[0][0]);
        close(pipe_fd[0][1]);

        close(pipe_fd[1][1]);
        dup2(pipe_fd[1][0], STDIN_FILENO);

        execlp("wc", "wc", "-l", NULL);
    }

    /* the parent process */
    else
    {
        /* close all the pipes */
        int i = 0;
        for(i = 0; i < 2; ++i)
        {
            close(pipe_fd[i][0]);
            close(pipe_fd[i][1]);
        }
        int ret = 0;
        do{
            ret = wait(NULL);
        }
        while(ret > 0);
    }
    return 0;
}
