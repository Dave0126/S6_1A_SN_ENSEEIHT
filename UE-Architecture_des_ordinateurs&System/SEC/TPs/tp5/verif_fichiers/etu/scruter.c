#include <stdio.h>    /* entrées sorties */
#include <unistd.h>   /* pimitives de base : fork, ...*/
#include <stdlib.h>   /* exit */
#include <sys/wait.h> /* wait */
#include <sys/types.h>
#include <string.h>   /* opérations sur les chaines */
#include <fcntl.h>    /* opérations sur les fichiers */


int main()
{
    int retour, desc_fic, desc_fic1, ifor;
    int wr_ret, rd_ret;

    char fichier[] = "temp";
    char wr_buffer[8];     /* buffer de lecture */
    char rd_buffer[100];

    /* ouverture du fichier en ecriture, avec autorisations rw- -r- ---*/
    /* avec création si le fichier n'existe pas : O_CREAT */
    /* avec vidange (raz du contenu) si le fichier existe: O_TRUNC */

    int counter = 1;
    for (int loop = 0 ; loop < 3 ; loop++ ) {
        desc_fic = open(fichier, O_RDWR | O_CREAT | O_TRUNC, 0640);

        /* traiter systématiquement les retours d'erreur des appels */
        if (desc_fic < 0) {
            printf("Erreur ouverture %s\n", fichier);
            exit(1);
        }
        for (ifor = 0; ifor < 10; ifor++) {
            bzero(wr_buffer, sizeof(wr_buffer));
            sprintf(wr_buffer, "%d\n", counter);
            wr_ret = write(desc_fic, wr_buffer, strlen(wr_buffer));
            if(wr_ret == -1) {
                perror("write file error:");
                return -1;
            }
            printf("\tProcessus numero %d a ECRIT %s\n",getpid(), wr_buffer);
            sleep(1);
            counter ++;
        }
        close(desc_fic);
        desc_fic1 = open(fichier, O_RDONLY, S_IRUSR | S_IWUSR | S_IROTH);
        lseek(desc_fic1, 0, SEEK_SET);
        retour = fork();
        /* Bonne pratique : tester systématiquement le retour des appels système */
        if (retour < 0) {   /* échec du fork */
            printf("Erreur fork\n");
            /* Convention : s'arrêter avec une valeur > 0 en cas d'erreur */
            exit(1);
        }
        if (retour == 0) {
            lseek(desc_fic1, 0, SEEK_SET);
            rd_ret = read(desc_fic1, rd_buffer, sizeof(rd_buffer));
            if(rd_ret == -1)
            {
                perror("read file error:");
                return -1;
            }
            printf("\tProcessus numero %d a LU :\n%s",getpid(), rd_buffer);

            exit(EXIT_SUCCESS);   /* Terminaison normale (0 = sans erreur) */
        }
    }
    return EXIT_SUCCESS;
}
