// Server's Client Handler Class
// New instance created for each client that connects to server

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class ServerClientHandler extends Thread {
    
	Socket clientSocket;
	BlockingQueue<Message> queue;
	Hashtable<String,Chat> chats;
	int clientId;
	Hashtable<Integer,PrintWriter> clientStreams;

	// Constructor
	public ServerClientHandler(Socket socket, BlockingQueue<Message> queue, Hashtable<String,Chat> chats, int clientId, Hashtable<Integer,PrintWriter> clientStreams) {
		this.clientSocket = socket;
		this.queue = queue;
		this.chats = chats;
		this.clientStreams = clientStreams;
		this.clientId = clientId;
	}
	
	public void run() {
		
		try {
			// Client Socket Input and Output streams
			PrintWriter SocketOutStream = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader SocketInStream =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
			// Read and Parse Header
			String header = SocketInStream.readLine();
			String[] header_fields = header.split("%%");
		
			String name = header_fields[0];
			String chatType = header_fields[1];
			String chatName = header_fields[2];
			String password = header_fields[3];
		
			// Creates new chat
			if (chatType.equals("new")){
				chats.put(chatName,new Chat(chatName,password));
			}
		
			// Check that chat exists
			if (!chats.containsKey(chatName)) {	
				return;
			}
			
			// Join chat, confirming correct password
			int status = chats.get(chatName).join(clientId, password);
			if (status == 0) {
				System.out.println("Incorrect Password");
				return;
			}
		
			// Add client socket's output stream to clientStreams map
			clientStreams.put(clientId, SocketOutStream);	
		
			// Add message to queue for message handler thread - alert members of chat to new participant
			queue.put(new Message(name, chatName,"Entering Chat", -1));
		
		
			// Loop to read messages from client socket's iinput stream
			String inputLine;
			while ((inputLine = SocketInStream.readLine()) != null) {
			
				// Exit sequence
				if (inputLine.equals("**EXIT**")) {
					
					//Close socket and streams
					clientSocket.close();
					SocketOutStream.close();
					SocketInStream.close();
					
					// Remove client from map
					chats.get(chatName).remove(clientId);
					
					// Alert remaining memers of chat
					queue.put(new Message(name, chatName,"Leaving Chat", -2));
				
					return;	
				}
			
				// Add message to queue for Message Handler thread
				queue.put(new Message(name, chatName,inputLine, clientId));
		    	}

		
		} catch (IOException | InterruptedException e) {
			// TODO
			// Update error handling
		}				
	}
}