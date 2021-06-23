#ifndef _EXTERN_H_
#define _EXTERN_H_

#include "def.h"
//commande externe
extern char cmdline[MAXLINE+1];
extern char avline[MAXLINE+1];

extern COMMAND cmd[PIPELINE];

extern char infile[MAXLINE+1];
extern char outfile[MAXLINE];

extern char *lineptr;
extern char *avptr;

extern int cmd_count;
extern int backgnd;
extern int append;

extern int lastpid;

extern char addr[80];
extern pid_t PID;


#endif    //
