// Server's Message Handler Thread
// One instance running per server
// Handles all messages received from clients, routes them to appropriate clients/chats

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class ServerMessageHandler extends Thread {

	BlockingQueue<Message> queue;
	Hashtable<String,Chat> chats;
	Hashtable<Integer,PrintWriter> clientStreams;
	
	// Constructor
	public ServerMessageHandler(BlockingQueue<Message> queue, Hashtable<String,Chat> chats, Hashtable<Integer,PrintWriter> clientStreams) {
		this.queue = queue;
		this.chats = chats;
		this.clientStreams = clientStreams;
	}
	
	public void run() {
		
		Message wrapper;
		String username;
		String chatName;
		String message;
		PrintWriter out;
		int clientId;
		
		while(true) {
			
			try {
				
				// New message popped from front of queue
				wrapper = queue.take();
		
				// Get attributes from Message object
				username = wrapper.getUserName();
				chatName = wrapper.getChatName();
				message = wrapper.getMessage();
				clientId = wrapper.getClientId();
				ArrayList<Integer> participants = chats.get(chatName).getParticipants();
			
				// Client has joined chat - alert all members via message
				if (clientId == -1) {
					for (int i = 0; i < participants.size(); i++) {		
						out = clientStreams.get(participants.get(i));
						out.println(username + " has joined the chat. " + participants.size() + " total participant(s)" );
					}
				}
				// Client has left chat - alert all members via message
				else if (clientId == -2) {
					for (int i = 0; i < participants.size(); i++) {		
						out = clientStreams.get(participants.get(i));
						out.println(username + " has left the chat. " + participants.size() + " total participant(s)" );
					}
				}
				// Normal message
				else {
					// Pass message on to all participants in chat, except client that sent message
					for (int i = 0; i < participants.size(); i++) {		
						if (participants.get(i) != clientId) {
							out = clientStreams.get(participants.get(i));
							// Add name of client who wrote message
							out.println(username + ": " + message);
						}
					}
				}	
				
			} catch (InterruptedException e) {
				// TODO 
				// Improve Error Handling
			}	
		}	
	}
}