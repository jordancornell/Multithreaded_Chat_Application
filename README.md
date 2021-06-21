# Multithreaded Chat Application

### Summary

This application was built as an exercise to work with socket programming, multithreading, and various data structures in Java.



### Project Structure



**The src/ folder in the project structure contains all java classes used to build the application:**

**Chat Server:**

**ChatServer.java** - Main Class for Multithreaded Chat Server. Creates Socket and accepts incoming client connections, calls threads to manage client connections and message handling.

**ServerMessageHandler.java** - Server's Message Handler Thread. Handles all messages received from clients, routes them to appropriate clients/chats.

**ServerClientHandler.java** - Server's Client Handler Class, new instance created for each client that connects to server.


**Message.java** - Message class acts as wrapper for every message sent through server. Structure contains fields to identify client and chat, as well as content of message

**Chat.java** - Structure to manage every active chat on the server


**Chat Client:**

**ChatClient.java** - Main class for multithreaded chat client. Takes user arguments, sets up socket connection with server, and reads user input. Creates instance of ClientThread class, which concurrently manages messages received from chat server

**ClientThread.java** - Manages messages received from chat server




### Example

Server is started with following command and optional arguments:

java ChatServer port=5000

Client starded with following command and optional arguments:

java ChatClient  address=127.0.0.1 port=5000

The following shows an example of three users connecting to a chat room.

Client 1 creates a new chat, selecting a name and password
![Client 1 Example](/example_graphics/client1.png)

Client 2 joins chat, using name and password created by client 1
![Client 2 Example](/example_graphics/client2.png)

Client 3 joins chat, using name and password created by client 1
![Client 3 Example](/example_graphics/client3.png)
