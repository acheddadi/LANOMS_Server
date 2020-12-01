import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Conversation implements Serializable {
	private ArrayList<User> participantList = new ArrayList<User>();
	private ArrayList<Message> chatLog = new ArrayList<Message>();
	private HashMap<User, Boolean> recent = new HashMap<User, Boolean>();
	
	// old user adds new user. User2 = new always
	Conversation(User user, User user2) {
		participantList.add(user);
		participantList.add(user2);
		recent.put(user, false);
		recent.put(user2, true);
	}
	
	public ArrayList<User> getParticipantList() {
		return participantList;
	}
	
	public ArrayList<Message> getChatLog() {
		return chatLog;
	}
	
	// Does recent to true for everyone except the sender
	public void addMessage(Message m, User u) {
		chatLog.add(m);
		recent.replaceAll((key,value)-> true);
		recent.replace(u, false);
	}
	
	public void addParticipant(User u) {
		participantList.add(u);
		recent.put(u, true);
	}
	
	public void removeParticipant(User u) {
		participantList.remove(u);
	}
	
	public boolean isParticipant(User u){
		return (participantList.contains(u));
	}
	
	public Message getMessage(int id) {
		return chatLog.get(id);
	}
	
	public void changeRecent(User u) {
		participantList.indexOf(u);
		
	}
	
	
	@Override
	public String toString() {
	    return "\nParticipant and Recent: " + this.recent + " Messages: " + this.chatLog;
	}
	
	
}
