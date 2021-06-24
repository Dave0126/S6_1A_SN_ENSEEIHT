# MINI-CHAT<br>
## Overview（概述）<br>
### For the server: <br>
1.  Run the server, the server first registers the signal processing function of SIGINT, which is used to implement the server exit function.<br>
2.  The server creates a public named pipe and opens the pipe file in read-only mode.<br>
3.  Read the structure data information from the client.<br>
4.  If the client’s data is identified as IS_NEW_CLIENT, it means that the client is communicating for the first time, and the server will create a corresponding private named pipe for this client, which is identified by the PID of the secondary client.<br>
5.  Next, the server continues to judge the client's message. If the message is CLIENT_QUIT, it means that the client has existed. At this time, the server needs to close the private pipe link.<br>
6.  If the client's data is a normal message, the server will reply to the client from the private channel.<br>
### For the client:<br>
1. Run the client program, the client will first send its identification information PID to the server through a shared pipeline.<br>
2. The server will create a corresponding private channel for the new client.<br>
3. Next, the client only needs to read the data in the corresponding private pipe.<br>
<br>
<br>

## How to compile？<br>
在linux系统环境下使用GCC进行编译。<br>
Server: `gcc -o server server.c`<br>
Client: `gcc -o client client.c`<br>
运行时需要先运行服务器端程序 `./server` ,以创建聊天中所使用的公共管道（pipe）等环境。之后再运行客户端程序 `./client` 。<br>
<br>
<br>
## How to use?<br>
* 聊天模式默认为广播（broadcast），即在该聊天室的所有人都可以收到消息。如果想只发给某一个人，在编写消息之前输入 `to xxx(要发送的人名) (要发送的消息)` 即可。<br>
* 程序退出时可以键入`quit`关闭客户端程序；`ctrl c`以关闭服务器端程序。<br>
