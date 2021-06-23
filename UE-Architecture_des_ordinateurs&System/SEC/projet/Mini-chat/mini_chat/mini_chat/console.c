/********************************************************************
*   Author: Guohao DAI / Groupe E / 1A SN
*   File Name: client.c
*   Function: Implemented group chat and private chat functions.
*   Last modified time: 04-06-2021
*********************************************************************/
/********************************************************************
*   Description: 
*   This program passes the client's pid to the server,
*   writes data to the server with the public FIFO,
*   and reads data from server with the private FIFO.
*********************************************************************/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <fcntl.h>
#include <unistd.h>
#include <signal.h>
#include <time.h>

#define PUBLIC_FIFO "Server_FIFO"
#define PRIVATE_FIFO "Client_FIFO_"
#define IS_NEW_CLIENT "handshake\n"
#define CLIENT_QUIT "quit\n"
#define BROADCAST_TO_ALL "Broadcast"
#define PRIVATE_MSG_HEADER "to:"
#define MAX_Client_Number 5
#define MAX_CLIENT_NAME_LEN 30
#define IS_PARENT 1

struct FIFO_Data{
    int client_pid;
    char client_name[MAX_CLIENT_NAME_LEN];
    char target_name[MAX_CLIENT_NAME_LEN];
    char message[100];
};

int New_Client_Flag;
int Quit_Flag;
int Client_Number;
int Client_PID_Box[MAX_Client_Number];
char Client_Name_Box[MAX_Client_Number][MAX_CLIENT_NAME_LEN];
int PublicFd, PrivateFd;
char Private_Name[20];
char* Private_FIFO_Name;
struct FIFO_Data Client_to_Server, Server_to_Client;

char* Get_Private_FIFO_Name(int Client_PID);
void Client_Write_Data(int Process, int Child_PID);
void Client_Read_Data(void);
void Show_Local_Time(void);
void Private_Chat_Filter_By_Name(char* Client_Message);


int main(){

    pid_t pid;

    /* Initial handshake message */
    printf("\n****** WELCOME TO THE MINI-CHAT ******\n\n>>>PLEASE ENTER YOUR NAME : ");
    scanf("%s", Client_to_Server.client_name);
    getchar();
    Client_to_Server.client_pid = getpid();
    strcpy(Client_to_Server.message, IS_NEW_CLIENT);
    strcpy(Client_to_Server.target_name, BROADCAST_TO_ALL);

    /* Get private FIFO name */
    Private_FIFO_Name = Get_Private_FIFO_Name(getpid());
    printf("Client PID is: %d\nClient Message is: %s\n", \
    Client_to_Server.client_pid, Client_to_Server.message);

    /* Client handshake with server, make a communication link */
    Client_Write_Data(!IS_PARENT, -1);
    Client_Read_Data();

    /* Keep communication */
    if((pid = fork()) < 0){
        printf("Fail to call fork()\n");
        exit(1);
    }

    /* Parent process sends data */
    else if(pid > 0){
        while(1){
            // printf("Please input message: ");
            fgets(Client_to_Server.message, 60, stdin);
            Client_to_Server.client_pid = getpid();
            Private_Chat_Filter_By_Name(Client_to_Server.message);
            Client_Write_Data(IS_PARENT, pid);
        }
    }

    /* Child process receives data */
    else{
        while(1){
            /* Read server struct data */
            Client_Read_Data();
        }
    }

}

/********************************************************************
*   Description: Generate FIFO file name based on Client PID.
*   Input: int Client_PID -> Used to identify the client
*********************************************************************/
char* Get_Private_FIFO_Name(int Client_PID){

    char TempBuffer[6];

    strcpy(Private_Name, PRIVATE_FIFO);
    sprintf(TempBuffer, "%d", Client_PID);
    //printf("TempBuffer is : %s\n", TempBuffer);
    strcat(Private_Name, TempBuffer);
    return Private_Name;

}


/********************************************************************
*   Description: Client sends data and handles the exit process.
*   Input: 
*       int Process -> Whether to be called by the parent process
*       int Child_PID -> PID of the child process
*********************************************************************/
void Client_Write_Data(int Process, int Child_PID){

    /* Open PUBLIC_FIFO with Write-Only mode */
    if((PublicFd = open(PUBLIC_FIFO, O_WRONLY)) > 0){

        /* Write struct date from Client to Server */
        if(write(PublicFd, &Client_to_Server, sizeof(struct FIFO_Data)) > 0){
            //printf("Success to write client_%d message to server!\n", getpid());
            close(PublicFd);
        }
        else{
            printf("Fail to write client data\n");
        }
        usleep(200000);

        /* Client exits communication */
        if(strcmp(Client_to_Server.message, CLIENT_QUIT) == 0){
            printf("Client_%d exit\n", Client_to_Server.client_pid);
            printf("Removed %s\n", Private_FIFO_Name);

            /* When the parent process calls the function, kill the child process */
            if(Process){
                kill(Child_PID, SIGSTOP);
                usleep(1000);
                printf("Child process has been stoped\n");
            }
            exit(0);
        }
    }
    else{
        printf("Fail to open PUBLIC_FIFO\n");
        exit(1);
    }

}


/********************************************************************
*   Description: Clients receive messages from public FIFO.
*********************************************************************/
void Client_Read_Data(void){

    /* Read server struct data */
    if((PrivateFd = open(Private_FIFO_Name, O_RDONLY)) > 0){
        if(read(PrivateFd, &Server_to_Client, sizeof(struct FIFO_Data)) > 0){
            Show_Local_Time();
            printf("%s\n", Server_to_Client.message);
            close(PrivateFd);
        }
    }
    else{
        printf("Fail to open %s\n", Private_FIFO_Name);
        exit(1);
    }

}


/********************************************************************
*   Description: Get current time through library function.
*********************************************************************/
void Show_Local_Time(void){

    time_t tmpcal_ptr;
	struct tm *tmp_ptr = NULL;
    time(&tmpcal_ptr);
    tmp_ptr = localtime(&tmpcal_ptr);
	printf ("<~ %d-%02d-%02d ", (1900+tmp_ptr->tm_year), (1+tmp_ptr->tm_mon), tmp_ptr->tm_mday);
	printf("%02d:%02d:%02d ~>\n", tmp_ptr->tm_hour, tmp_ptr->tm_min, tmp_ptr->tm_sec);

}


/********************************************************************
*   Description: Filter target users and valid information in private message content.
*   Input: char* Client_Message -> The original message received by the client
*********************************************************************/
void Private_Chat_Filter_By_Name(char* Client_Message){

    int offset;
    char buffer[2];
    char client_name_buffer[MAX_CLIENT_NAME_LEN] = {0};

    /* Determine whether it is privately sent through the PRIVATE_MSG_HEADER */
    if(strncmp(Client_Message, PRIVATE_MSG_HEADER, strlen(PRIVATE_MSG_HEADER)) == 0){
        
        printf("\n");
        offset = strlen(PRIVATE_MSG_HEADER);

        /* Filter spaces of client pid */
        while(*(Client_Message + offset) == ' '){
            offset += 1;
        }
        /* Get a valid username */
        while(offset < strlen(Client_Message) && (*(Client_Message + offset) != ' ')){
            sprintf(buffer, "%c", *(Client_Message + offset));
            strcat(client_name_buffer, buffer);
            offset ++;
        }
        /* Filter spaces of client message */
        while(*(Client_Message + offset) == ' '){
            offset += 1;
        }

        /* Save a valid user name and valid information */
        strcpy(Client_to_Server.message, (Client_Message + offset));
        strcpy(Client_to_Server.target_name, client_name_buffer);
    }

    /* Broadcasting information mode */
    else{
        //printf("BROADCAST_TO_ALL\n");
        strcpy(Client_to_Server.target_name, BROADCAST_TO_ALL);
        strcpy(Client_to_Server.message, Client_Message);
    }

}


