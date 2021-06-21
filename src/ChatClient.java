// Main class for multithreaded chat client
// This class takes user arguments, sets up socket connection with server, and reads user input
// Creates instance of ClientThread class, which concurrently manages messages received from chat server

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ChatClient {
	
	public static void main(String[] args) throws Exception {
	
		// default address and port
		int port = 5000;
		String address = "127.0.0.1";
		
		// Parse command line arguments from user, update variables
		String[] parseArg;
		for (String arg:args) {
			parseArg = arg.split("=");
			if (parseArg[0].equals("address")){
				address = parseArg[1];
			}
			else if (parseArg[0].equals("port")){
				port = Integer.parseInt(parseArg[1]);
			}
		}
		
		// New scanner to read user input
		// Collect client's name, new/join, name of chat, and password
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter name: ");
	    String name = scanner.nextLine();  
	    System.out.print("Join chat(join) or create new(new)? ");
	    String chatType = scanner.nextLine(); 
	    System.out.print("Name of chat: ");
		String chatName = scanner.nextLine();
		System.out.print("Password: ");
	    String password = scanner.nextLine();

	    // From these arguments collected from user, create header message to send to server
	    String combined = name + "%%" + chatType + "%%" + chatName + "%%" + password;

	    System.out.println("Type '**EXIT**' to disconnect");
	
		// Establish socket connection
		Socket socket = new Socket(address, port);	
		
		// Socket input/output streams
		PrintWriter socketOutStream = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader socketInStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		// Standard Input stream to read client messages
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		
		// Send header message to Server
		socketOutStream.println(combined);
		
		// Initialize client thread to manage incoming messages from Server
		ClientThread object = new ClientThread(socket, socketInStream);
	    object.start();
		
	    // Loop to read user input and send as message to server
		String userInput;
        while ((userInput = stdIn.readLine()) != null) {
        	
        	// Send via socket connection
        	socketOutStream.println(userInput);

        	// Exit sequence
            if (userInput.equals("**EXIT**")) {
        		System.out.println("Closing connection...");
        		socket.close();
        		stdIn.close();
        		socketOutStream.close();
        		socketInStream.close();
        		return;
        	}
        }	
	}
}