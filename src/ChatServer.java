// Main Class for Multithreaded Chat Server
// Creates Socket and accepts incoming client connections
// Initializes all necessary data structures
// Calls threads to manage client connections and message handling

import java.net.*;
import java.io.*;
import java.util.Hashtable;
import java.util.concurrent.*;

public class ChatServer {

	public static void main(String[] args) throws Exception {
		int port = 5000;
		
		// Evaluate User Arguments
		String[] parseArg;
		for (String arg:args) {
			parseArg = arg.split("=");
			if (parseArg[0].equals("port")){
				port = Integer.parseInt(parseArg[1]);
			}
		}

		// Initialize all Data Structures for Server Operation:
		// Hashtable to store info about each active chat
		Hashtable<String,Chat> chats = new Hashtable<String,Chat>();
		// Hashtable to map each client id to its socket output stream
		Hashtable<Integer,PrintWriter> clientStreams = new Hashtable<Integer,PrintWriter>();
		// Blocking queue to store info about messages passing through server
		BlockingQueue<Message> queue = new ArrayBlockingQueue<Message>(1000);
		
		// Open Server Socket
		ServerSocket serverSocket = new ServerSocket(port);
		
		// Start Server Message Handler Thread
		ServerMessageHandler consumer = new ServerMessageHandler(queue, chats, clientStreams);
		consumer.start();

		// Client ID to keep track internally of each new client that joins
		int clientId = 1;
		
		// Loop to accept any incoming client requests, and pass them off to new handler thread
		while(true) {
			
			// Accept incoming socket connection
			Socket clientSocket = serverSocket.accept();
			
			//New Client Handler thread
			ServerClientHandler object = new ServerClientHandler(clientSocket,queue, chats, clientId, clientStreams);
			object.start();
			
			clientId++;		
		}
	}
}