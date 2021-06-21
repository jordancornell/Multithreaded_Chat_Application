// Structure to manage every active chat on the server

import java.util.ArrayList;

public class Chat {
	
	private String chatName;
	private String password;
	private ArrayList<Integer> participants = new ArrayList<Integer>();
	
	// Constructor - set Chat Name and Password
	public Chat(String chatName, String password) {
		this.chatName = chatName;
		this.password = password;
	}
	
	// Join method - add client to chat after confirming password
	public int join(int userId, String passwd) {
		if (password.equals(passwd)) {
			participants.add(userId);
			return 1;
		}
		else {
			// Incorrect Password
			return 0;
		}
	}
	// Remove client from chat
	public void remove(int userId) {
		participants.remove(participants.indexOf(userId));
		
	}
	
	
	// Getter - Return list of active participants in chat
	public ArrayList<Integer> getParticipants() {
		return participants;
	}
	
}
