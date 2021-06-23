#ifndef _DEF_H_
#define _DEF_H_
#include <stdio.h>
#include <stdlib.h>

#define MAXLINE 1024    //Maximum length of input line

#define MAXARG 20       //Maximum number of parameters for each simple command
#define PIPELINE 5      //Maximum number of simple commands in a pipeline line
#define MAXNAME 100     //Maximum length of redirected file name

#define err_sys(info) { \
    fprintf(stderr, "%s:%s\n", info, strerror(errno));   \
    exit(EXIT_FAILURE); \
}

#define ERR_EXIT(m)    \
    do {               \
        perror(m);     \
        exit(EXIT_FAILURE); \
    } while(0);

typedef struct command {
    char *args[MAXARG-1];    //List of parsed command parameters
    int infd;                //Input descriptor
    int outfd;               //Output descriptor
} COMMAND;

#endif  //_DEF_H_
