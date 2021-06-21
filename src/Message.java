// Message class acts as wrapper for every message sent through server
// Structure contains fields to identify client and chat, as well as content of message

public class Message {

	private String username;
	private String chatName;
	private String message;
	private int clientId;
	
	
	// Constructor
	public Message(String username, String chatName, String message, int clientId) {
		this.username = username;
		this.chatName = chatName;
		this.message = message;
		this.clientId = clientId;
	}
	
	// Getter methods	
	public String getUserName() {
		return username;
	}
	
	public String getChatName() {
		return chatName;
	}
	
	public String getMessage() {
		return message;
	}
	public int getClientId() {
		return clientId;
	}
	
}
