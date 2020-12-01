import java.io.Serializable;

public class ApplicationSettings implements Serializable {
	private boolean soundNotification;
	private boolean popupNotification;
	private int autoDelete;
	
	ApplicationSettings(){
		this.soundNotification = true;
		this.popupNotification = true;
		this.autoDelete = 30;
	}
	
	// Getters and Setters
	
	public boolean isSoundNotification() {
		return soundNotification;
	}

	public void setSoundNotification(boolean soundNotification) {
		this.soundNotification = soundNotification;
	}

	public boolean isPopupNotification() {
		return popupNotification;
	}

	public void setPopupNotification(boolean popupNotification) {
		this.popupNotification = popupNotification;
	}

	public int getAutoDelete() {
		return autoDelete;
	}

	public void setAutoDelete(int autoDelete) {
		this.autoDelete = autoDelete;
	}
	
	@Override
	public String toString() {
	    return "\nSound Notification: " + this.soundNotification + " Popup Notification: " + this.popupNotification + " Auto Delete: " + this.autoDelete + " days";
	}
	

}
