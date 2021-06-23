/* the TP of signal */
/* 1.2 Gestion des masques de signaux */
/* @ Author Guohao. */

#include <stdio.h>    /* entrées sorties */
#include <unistd.h>   /* pimitives de base : fork, ...*/
#include <stdlib.h>   /* exit */
#include <signal.h>   /* traitement des signaux */
#include <sys/wait.h> /* wait */

/* Le message affiché par le traitant
 * devra exactement être la chaı̂ne
 * constituée par le mot Reception,
 * suivi d’un espace, suivi du numéro du signal recu.
 */
void handler_sigint(int signal_num) {
    printf("\nReception %d\n",signal_num) ;
    return ;
}

/* dormir pendant nb_secondes secondes  */
/* à utiliser à la palce de sleep(duree), car sleep s'arrête */
/* dès qu'un signal non ignoré est reçu */
void dormir(int nb_secondes)
{
    int duree = 0 ;
    while (duree < nb_secondes) {
        sleep(1) ;
        duree++ ;
    }
}

int main()
{
    /* Associer à SIGUSR1 et SIGUSR2
     * un traitant affichant le numéro du signal reçu.
     */
    signal(SIGUSR1, handler_sigint) ;
    signal(SIGUSR2, handler_sigint) ;

    sigset_t signal ;
    sigemptyset(&signal) ;
    /* Masquer les signaux SIGINT et SIGUSR1 */
    sigaddset(&signal, SIGINT) ;
    sigaddset(&signal, SIGUSR1) ;
    sigprocmask(SIG_BLOCK, &signal, NULL) ;
    /* Attendre 10s */
    /* S’envoier 2 SIGUSR1 */
    dormir(10) ;
    kill(getpid(), SIGUSR1) ;
    kill(getpid(), SIGUSR1) ;
    /* Attend 5 secondes */
    /* S’envoie 2 SIGUSR2 */
    dormir(5) ;
    kill(getpid(), SIGUSR2) ;
    kill(getpid(), SIGUSR2) ;
    /* Démasquer SIGUSR1 */
    sigemptyset(&signal) ;
    sigaddset(&signal, SIGUSR1) ;
    sigprocmask(SIG_UNBLOCK, &signal, NULL) ;
    /* Attendre 10s */
    dormir(10) ;
    /* Démasque SIGINT */
    sigemptyset(&signal) ;
    sigaddset(&signal, SIGINT) ;
    sigprocmask(SIG_UNBLOCK, &signal, NULL) ;
    /* Afficher un message de terminaison
     * commençant (exactement) par le mot Salut
     */
     printf("\nSalut!\n") ;
     return EXIT_SUCCESS ;
}
