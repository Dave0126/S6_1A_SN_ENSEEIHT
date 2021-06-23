#include <stdio.h>    /* entrées sorties */
#include <unistd.h>   /* pimitives de base : fork, ...*/
#include <stdlib.h>   /* exit */
#include <sys/wait.h> /* wait */
#include <string.h>   /* opérations sur les chaines */

#define NB_FILS 2     /* nombre de fils */

 int main()
{
    int fils, retour, v_lue, pid ;

    int pipe_p2f[NB_FILS+1][2] ;      /* pipe_p2f[0] non utilisé */

    /* Créet NB_FILS pipes */
    for (int i = 1 ; i <= NB_FILS ; i++) {
        retour = pipe(pipe_p2f[i]) ;
        if (retour == -1) {   /* échec du pipe */
            printf("Erreur pipe\n") ;
            exit(1) ;
        }
    }

    printf("\nJe suis le processus principal de pid %d\n", getpid()) ;

    for (fils = 1 ; fils <= NB_FILS ; fils++) {
        retour = fork() ;

        /* Bonne pratique : tester systématiquement le retour des appels système */
        if (retour < 0) {   /* échec du fork */
            printf("Erreur fork\n") ;
            /* Convention : s'arrêter avec une valeur > 0 en cas d'erreur */
            exit(1) ;
        }

        /* fils */
        if (retour == 0) {
            /* fermer l'extrémité 1 de son pipe : le fils est lecteur */
            close(pipe_p2f[fils][1]) ;

            /* fermer les extrémités de tous les autres pipes */
            for (int i = 1 ; i <= NB_FILS ; i++) {
                if (i!=fils) {
                    close(pipe_p2f[i][0]) ;
                    close(pipe_p2f[i][1]) ;
                }
            }
            /* lire ce qui arrrive dans le pipe */

            while (read(pipe_p2f[fils][0], &v_lue, sizeof(int)) > 0) {
                printf("\n     Processus de pid %d - recu : %d\n", getpid(), v_lue) ;
            }
            /* fermer l'extrémité 0 de son pipe */
            close(pipe_p2f[fils][0]) ;

            /* Important : terminer un processus par exit */
            exit(EXIT_SUCCESS) ;   /* Terminaison normale (0 = sans erreur) */
        }

        /* pere */
        else {
            printf("\nProcessus de pid %d a cree un fils numero %d, de pid %d \n",
                getpid(), fils, retour) ;
        }
    }
    /* fermer l'extrémité 0 de tous les pipes : le père est rédacteur */
    for (int i = 1 ; i <= NB_FILS ; i++) {
        close(pipe_p2f[i][0]) ;
    }

    pid = getpid() ;

    /* envoyer son pid à chaque fils */
    for (fils = 1 ; fils <= NB_FILS ; fils++) {
        sleep(1) ;
        retour = write(pipe_p2f[fils][1], &pid, sizeof(int)) ;
        if (retour < 0) {
            printf("\nProcessus Principal : Erreur write %d.\n", retour) ;
        }
    }
    /* fermer l'extrémité 1 de tous les pipes : fin des envois */
    /* les porchains read effectués dans les fils renvoient 0  */
    for (int i = 1 ; i <= NB_FILS ; i++) {
        close(pipe_p2f[i][1]) ;
    }

    printf("\nProcessus Principal termine.\n") ;
    return EXIT_SUCCESS ;
}
