/********************************************************************
*   Author: Guohao DAI / Groupe E / 1A SN
*   File Name: server.c
*   Function: Implemented group chat and private chat functions.
*   Last modified time: 04-06-2021
*********************************************************************/
/********************************************************************
*   Description: 
*   1. Bind the SIGINT signal.
*   2. Create a public FIFO and open it with read-only mode.
*   3. Read the client's data, determine whether the client is new or not.
*   4. If the client is new, create a private pipe based on this client's PID.
*   5. Writes some message to client with the private FIFO.
*   6. Reads data from client with the public FIFO.
*   7. If "quit" is read from the client, close the private FIFO and end the session.
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

void Create_FIFO(char *FIFO_Name);
char* Get_Private_FIFO_Name(int Client_PID);
void Server_Sigcatch(int num);
void Store_Private_FIFO_Name(void);
void Server_Send_Message(void);
void Delete_Client_Data(void);


int main(){

    Client_Number = 0;
    Quit_Flag = 0;
    // New_Client_Flag = 0;

    /* Bind the SIGINT signal */
    if(signal(SIGINT, &Server_Sigcatch) == SIG_ERR){
        printf("Couldn't register signal handler\n");
        exit(1);
    }

    /* Create Public FIFO File */
    Create_FIFO(PUBLIC_FIFO);

    while(1){

        if((PublicFd = open(PUBLIC_FIFO, O_RDONLY)) < 0){
            printf("Fail to open PUBLIC_FIFO\n");
            exit(1);
        }

        /* Read the strcut data of the public FIFO */
        if(read(PublicFd, &Client_to_Server, sizeof(struct FIFO_Data)) > 0){

            printf("Client Pid is : %d\n", Client_to_Server.client_pid);
            printf("Client Message is : %s", Client_to_Server.message);
            Private_FIFO_Name = Get_Private_FIFO_Name(Client_to_Server.client_pid);

            /* Create the private FIFO for a new client */
            if(strcmp(Client_to_Server.message, IS_NEW_CLIENT) == 0){
                printf("This is a new client!\n");
                New_Client_Flag = 1;
                Create_FIFO(Private_FIFO_Name);
                Store_Private_FIFO_Name();
            }
            /* If the client exits, cut off communication */
            if(strcmp(Client_to_Server.message, CLIENT_QUIT) == 0){
                unlink(Private_FIFO_Name);
                Delete_Client_Data();
                Client_Number --;
                Quit_Flag = 1;
                printf("Closed Client_%d Private FIFO\n", Client_to_Server.client_pid);
                printf("Client Number is : %d\n\n", Client_Number);
            }
            /* Server send a reply message to the client */
            Server_Send_Message();
        }
        else{
            printf("Read Date error!\n");
            exit(1);
        }
    }
    return 0;
}


/********************************************************************
*   Description: Create a FIFO file based on the string "FIFO_Name".
*   Input: char *FIFO_Name -> Use to represent FIFO file name
*********************************************************************/
void Create_FIFO(char *FIFO_Name){

    int TempFd;

    if((TempFd = open(FIFO_Name, O_RDONLY)) == -1){
        umask(0);
        mknod(FIFO_Name, S_IFIFO|0666, 0);
        printf("%s has been bulid\n", FIFO_Name);
    }
    else{
        close(TempFd);
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
*   Description: Register SIGINT signal processing function.
*   Input: int num -> Signal value
*********************************************************************/
void Server_Sigcatch(int num){

    printf("\nServer is exiting...\n");
    unlink(PUBLIC_FIFO);
    printf("Removed %s\nSee you again ! \n", PUBLIC_FIFO);
    exit(0);

}


/********************************************************************
*   Description: Save client PID and user name.
*********************************************************************/
void Store_Private_FIFO_Name(void){

    Client_PID_Box[Client_Number] = Client_to_Server.client_pid;
    strcpy(Client_Name_Box[Client_Number], Client_to_Server.client_name);
    printf("The %d has been recorded\n", Client_PID_Box[Client_Number]);
    Client_Number ++;
    printf("Client Number is : %d\n", Client_Number);

}


/********************************************************************
*   Description: The server sends information based on private and public message.
*********************************************************************/
void Server_Send_Message(void){

    int count;
    int target_count;
    int source_count;

    /* Display the target users of the client */
    printf("Client Target is: %s\n", Client_to_Server.target_name);

    /* Private message */
    if(strcmp(Client_to_Server.target_name, BROADCAST_TO_ALL) != 0){

        /* Find the location of the target username */
        for(target_count = 0; target_count < Client_Number; target_count ++){
            if(strcmp(Client_to_Server.target_name, Client_Name_Box[target_count]) == 0)
            break;
        }
        /* Find the location of the target client PID */
        for(source_count = 0; source_count < Client_Number; source_count ++){
            if(Client_to_Server.client_pid == Client_PID_Box[source_count])
            break;
        }

        /* Send private message data */
        printf("Private FIFO Name : %s\n", Get_Private_FIFO_Name(Client_PID_Box[target_count]));
        if((PrivateFd = open(Get_Private_FIFO_Name(Client_PID_Box[target_count]), O_WRONLY)) > 0){
            Server_to_Client.client_pid = Client_to_Server.client_pid;
            sprintf(Server_to_Client.message, ">> [%s] said to you : ", Client_Name_Box[source_count]);
            strcat(Server_to_Client.message, Client_to_Server.message);
            if(write(PrivateFd, &Server_to_Client, sizeof(struct FIFO_Data)) > 0){
                printf("Write message to Client_%d Success!\n", Client_PID_Box[target_count]);
                close(PrivateFd);
            }
        }
    }

    /* Public message */
    else{

        /* Filter message content */
        if(New_Client_Flag){
            sprintf(Server_to_Client.message, "ATTENTION! [%s] join in the discussion. \n", Client_to_Server.client_name);
            New_Client_Flag = 0;
        }
        else if(Quit_Flag){
            sprintf(Server_to_Client.message, "ATTENTION! [%s] quit the discussion. \n", Client_to_Server.client_name);
            Quit_Flag = 0;
        }
        else{
            sprintf(Server_to_Client.message, ">> [%s] said: ", Client_to_Server.client_name);
            strcat(Server_to_Client.message, Client_to_Server.message);
        }

        /* Send broadcast data cyclically */
        for(count = 0; count < Client_Number; count ++){
            /* Skip the data sent by the client */
            // if((Client_PID_Box[count] == Client_to_Server.client_pid) && !New_Client_Flag){
            //     continue;
            // }
            if((PrivateFd = open(Get_Private_FIFO_Name(Client_PID_Box[count]), O_WRONLY)) > 0){
                Server_to_Client.client_pid = Client_to_Server.client_pid;
                if(write(PrivateFd, &Server_to_Client, sizeof(struct FIFO_Data)) > 0){
                    printf("Write message to Client_%d Success!\n", Client_PID_Box[count]);
                    close(PrivateFd);
                }
            }
            usleep(100000);
        }
    }

    printf("\n");

}


/********************************************************************
*   Description: Delete Information of exited client.
*********************************************************************/
void Delete_Client_Data(void){

    int local;
    int count;

    for(local = 0; local < Client_Number; local++){
        if(Client_to_Server.client_pid == Client_PID_Box[local]){
            break;
        }
    }
    for(count = local; count < Client_Number; count ++){
        Client_PID_Box[count] = Client_PID_Box[count + 1];
        strcpy(Client_Name_Box[count], Client_Name_Box[count + 1]);
    }

}

