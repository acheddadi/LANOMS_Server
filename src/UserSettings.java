import java.io.Serializable;

public class UserSettings implements Serializable {
	private byte[] image;
	private String status;
	private String message;
	
	UserSettings(){
		this.status = "Offline";
		this.message = "Empty";
	}
	
	//Getters and setters
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String s) {
		this.status = s;
	}
	
	public byte[] getIMAGE() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
	    return "\nStatus: " + this.status + "	Message: " + this.message;
	}
	
}
