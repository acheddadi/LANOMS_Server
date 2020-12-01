import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
	protected User user;
	protected ArrayList<Conversation> conversations;
	protected UserSettings uSettings;
	protected ApplicationSettings aSettings;
	
	Account(User user){
		this.user = user;
		this.aSettings = new ApplicationSettings();
		this.uSettings = new UserSettings();
		conversations = new ArrayList<Conversation>();
	}

	

	
	public void removeConversation(int id) {
		conversations.remove(id);
	}
	
	public void addToConvo(int id, Account a){
		conversations.get(id).addParticipant(a.user);
		a.conversations.add(this.conversations.get(id));
	}
	
	public int getConvoID(Conversation x) {
		return conversations.indexOf(x);
	}
	
	@Override
	public String toString() {
	    return "\nUser: " + this.user 
	    		+ "\nConversations: " + this.conversations 
	    		+ "\nUser Settings: " + this.uSettings 
	    		+ "\nApplication Settings: " + this.aSettings;
	}
	
}

