#include "jobs.h"
#include <stdio.h>
#include <malloc.h>
#include <string.h>
#include "extern.h"
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>

void ctrl_z(int sig) // Ctrl+Z
{
	NODE *pi=NULL;

	pi=(NODE *)malloc(sizeof(NODE))	;
	pi->num=NUM;
	pi->pid=PID;
	strcpy(pi->state,"stopped");
	strcpy(pi->jcmd," ");
	head=jobs_link(pi);
	kill(PID, SIGSTOP);
}

NODE *jobs_link(NODE *pi)
{
	NODE *pf,*pb;
	pb=head;//Save the head pointer of the list of background running programs
	if(head == NULL) {//Case 1: There is no background process execution in the list
		NUM=pi->num;
		head=pi;
		pi->next=NULL;//Create a list and save it in the global variable head
	} else {//Case 2: Add a new background execution process to the list
		while(pb->next != NULL) {//Loop through the list until the last item in the list
			pf=pb;
			pb=pb->next;
		}
		NUM=pi->num;
		pb->next=pi;
		pi->next=NULL;//Add a new background execution process to the list
	}
	NUM++;
	return (head);
}

void output_jobs()
{
    NODE *p;
	p=head;
	if(head != NULL) {
		do {
			printf("[%d]  %d  %s  %s\n",p->num,p->pid,p->state,p->jcmd);
			p=p->next;
		}while(p != NULL);
	}
	else
		printf("no jobs in list!\n");

}

void fg_jobs()
{
	int num;
	NODE *p=NULL;
	pid_t pid=0;

	p=head;

	if(cmd[0].args[1] != NULL) {
		sscanf(cmd[0].args[1],"%d",&num);

		if(p == NULL) {
			printf("no jobs!!\n");
		}
		while(p != NULL) {
			if(num == p->num) {
				pid=p->pid;
				break;
			}
			p=p->next;
		}
		kill(pid,SIGCONT);
		waitpid(pid,NULL,WUNTRACED);
	}
}

void bg_jobs()
{
	int num;
	NODE *p=NULL;
	pid_t pid=0;

	p=head;

	if(cmd[0].args[1] != NULL) {
		sscanf(cmd[0].args[1],"%d",&num);

		if(p == NULL) {
			printf("no jobs!!\n");
		}
		while(p != NULL) {
			if(num == p->num) {
				pid=p->pid;
				break;
			}
			p=p->next;
		}
		kill(pid,SIGCONT);
	}
}
