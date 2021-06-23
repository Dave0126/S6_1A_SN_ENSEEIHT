#ifndef _JOBS_H_
#define _JOBS_H_

#include <sys/types.h>
int NUM;
typedef struct node
{
	int num;
	pid_t pid;
	char state[10];
	char jcmd[40];
	struct node *next;
}NODE;	// structure of jobs's node

NODE *jobs_link(NODE *pi);
void output_jobs(void); // print
void ctrl_z(int sig);
void fg_jobs(void);
void bg_jobs(void);

#endif  //_JOBS_H_
