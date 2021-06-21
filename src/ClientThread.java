// Client thread to manage incoming messages from server
// One instance per client, called from ChatClient
// Reads incoming messages and prints to console

import java.net.*;
import java.io.*;

public class ClientThread extends Thread {
    
	Socket socket;
	BufferedReader in;
	
	// Constructor
	public ClientThread(Socket socket, BufferedReader in) {
		this.socket = socket;
		this.in = in;
	}
	
	public void run() {
		
		try {
			// Read from socket input stream
			String line = in.readLine();
			
			while (line != null) {
				// Print message to console
				System.out.println(line);
				line = in.readLine();
			}
		
		} catch (IOException e) {
			// TODO
			// Improve error Handling
			// Socket and IO streams closed from ChatClient
		}	
	}
}