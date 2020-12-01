import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	private User sender;
	private Date date;
	private String message;
	
	
	Message(User sender, String message){
		this.sender = sender;
		this.message = message;
		date = new Date();
	}
	
	public User getSender () {
		return this.sender;
	}
	
	public String getDate() {
		return this.date.toString();
	}
	
	public String getMessage() {
		return this.message;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
	    return "\nUser: " + this.sender.getUsername() + "	Date: " + this.date + "	Message: " + this.message;
	}
}

