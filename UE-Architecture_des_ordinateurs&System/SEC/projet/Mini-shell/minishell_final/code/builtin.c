#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "builtin.h"
#include "parse.h"
#include "extern.h"
#include "jobs.h"

typedef void (*CMD_HANDLER)(void);  //重定义了一个void(void)型函数指针

typedef struct builtin_cmd {
    char *name;
    CMD_HANDLER handler;
} BUILTIN_CMD;

void do_exit(void);
void do_cd(void);
void do_jobs(void);
void do_bg(void);
void do_fg(void);

BUILTIN_CMD builtins[] = {
    {"exit", do_exit},
    {"cd", do_cd},
    {"jobs", do_jobs},
    {"bg", do_bg},
    {"fg", do_fg},
    {NULL, NULL}
};


/*
 * Internal command analysis
 * The internal command returns 1, otherwise it returns 0
 */
int builtin(void)
{
    int i = 0;
    int found = 0;
    while(builtins[i].name != NULL) {
        if(check(builtins[i].name)) {
            builtins[i].handler();
            found = 1;
            break;
        }
        i++;
    }

        return found;
}

void do_exit()
{
    printf("\n------EXIT SUCCESSFULLY------!\n");
    exit(EXIT_SUCCESS);
}

void do_cd()
{
    if(check("~")) {
        chdir("/root");
    } else {
        get_command(0);
        chdir(cmd[0].args[0]);
    }
}


//Circular output of all process information allowed in the background
void do_jobs()
{
    output_jobs();
}

void do_bg(void)
{
    get_command(0);
    fg_jobs();
}

void do_fg(void)
{
    get_command(0);
    bg_jobs();
}
