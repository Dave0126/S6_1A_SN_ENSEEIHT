#include <stdio.h>    /* entrées sorties */
#include <unistd.h>   /* pimitives de base : fork, ...*/
#include <stdlib.h>   /* exit */
#include <sys/wait.h> /* wait */
#include <string.h>   /* opérations sur les chaines */

void main(){
    int p1[2],p2[2];
    //signal(SIGCHLD, handler_sigchld);
    /* Bonne pratique : tester systématiquement le retour des appels système */
    if (pipe(p1) == -1) {   /* échec du pipe */
        perror("Erreur pipe p1\n") ;
        /* Convention : s'arrêter avec une valeur > 0 en cas d'erreur */
        exit(1) ;
    }
    if (pipe(p2) == -1) {   /* échec du pipe */
        perror("Erreur pipe p2\n") ;
        /* Convention : s'arrêter avec une valeur > 0 en cas d'erreur */
        exit(1) ;
    }
    int pid1 = fork();

    if (pid1 < 0) {   /* échec du pipe */
        perror("Erreur fork\n") ;
        /* Convention : s'arrêter avec une valeur > 0 en cas d'erreur */
        exit(1) ;
    }

    if (pid1 == 0) {
        dup2(p1[1], 1);
        close(p1[0]);
        close(p2[1]);
        close(p2[0]);
        execlp("who", "who", NULL, NULL);
        printf("----who----");
    } else {
        if (fork() == 0) {
            dup2(p1[0], 0);
            dup2(p2[1], 1);
            close(p1[0]);
            close(p2[1]);
            close(p1[1]);
            close(p2[0]);
            execlp("grep", "grep", "nom_utilisateur", NULL);
            printf("----grep----");
            exit(0);
        }
    }
    dup2(p2[0], 0);
    close(p2[0]);
    close(p1[0]);
    close(p1[2]);
    execlp("wc", "wc", "-l", NULL);
    printf("----grep----");
}
