import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ServerControllerOLD {
	
	//static HashMap<String, Account> account = new HashMap<String, Account>();
	
	static ArrayList<Account> account = new ArrayList<Account>();
	static HashMap<String, Account> mapper = new HashMap<String, Account>();
	
	public static void main(String[] args) throws Exception{
		
		// Make accounts (can make it to load existing accounts in future).
		makeAccounts();
		makeConversation(0,1);
		makeConversation(1,2);
		makeMessage("ali", "Hello Nik", 0);
		makeMessage("nik", "Hi Ali", 0);
		
		makeConversation(2, 1);
		makeConversation(1,2);
		backup();
		saveAccount(mapper.get("ali"));
		pullAccount("ali");
		// User Index 1 adds index 0 to convo
		account.get(1).addToConvo(1, account.get(0));
		
	}
		
	
	public static String getUserCount() {
		// Input - None
		// Output - Number of users in the system
		return account.size() + "";
	}
	
	public static String getUserInfo(int i) {
		// Input: Index of array (i) for user
		// Output: User information as Username \n Message \n Status \n Department \n Email
		return account.get(i).user.getUsername() + "\n" 
				+ account.get(i).uSettings.getMessage() + "\n" 
				+ account.get(i).uSettings.getStatus() + "\n"
				+ account.get(i).user.getDepartment() + "\n" 
				+ account.get(i).user.getEmail() + "\n";
	}
	
	public byte[] getUserDisplayPicture(int i) {		
		// Dont worry about image xfer yet, I need to save as byte for the recall. Its saved as javafx atm. 
		// Input: Index of array (i) for user
		// Output: Image
		return account.get(i).uSettings.getIMAGE();
	}
	
	public static String getConversationCount(int i) {
		// Input: Index of account array
		// Output: Size of conversation
		return account.get(i).conversations.size() + "";
	}
	
	public static String getMessageCount(int i, int j) {
		// Input: i = index of account array
		//        j = index of conversation array
		// Output: number of messages in the conversation
		return account.get(i).conversations.get(j).getChatLog().size() + "";
	}
	
	public static String getMessage(int i, int j, int k) {
		// Input: i = index of account array
		//        j = index of conversation array
		//        k = index of message array
		// Output: The message as NAME \n MESSAGE
		
		return account.get(i).conversations.get(j).getChatLog().get(k).getSender() + "\n"
				+ account.get(i).conversations.get(j).getChatLog().get(k).getMessage();
	}
	
	
	
	// Add additional accounts 
	public static void makeAccounts(){
		// Existing accounts
		
		User ali = new User("ali", null, "Ali","CS","Ali@email.com");
		Account a = new Account(ali);
		account.add(a);
		mapper.put(a.user.getUsername(), a);
		
		User nik = new User("nik", null, "Nik", "CS", "Nik@email.com");
		Account n = new Account(nik);
		account.add(n);
		mapper.put(n.user.getUsername(), n);
		
		User shree = new User("shree", null, "Shree", "CS","Shree@email.com");
		Account s = new Account(shree);
		account.add(s);
		mapper.put(s.user.getUsername(), s);
	}
	
	public static void makeConversation(int i, int j){ 	// make conversations in server
		// Input = User1 index, User 2 index
		Conversation cov = new Conversation(account.get(i).user, account.get(j).user);
		account.get(i).conversations.add(cov);
		account.get(j).conversations.add(cov);
	}
	
	
	
	public static String checkValidLogin(String name, String pass) {
		// Input: Username Password
		// Output: String true or false. true = Valid pass. false = Invalid pass.
		return (mapper.get(name).user.verifyPassword(pass)) + ""; // checks if password is valid
	}
	
	
	public static String makeMessage(String username, String message, int ID) {
		// Input: String Username, String message, int Index of conversation
		// Saves to the server.
		// Output = String: "#OF PARTICIPANTS \n partcipant1...n + MESSAGE"
		
		Message mes = new Message(mapper.get(username).user, message);
		mapper.get(username).conversations.get(ID).addMessage(mes, mapper.get(username).user);
		String msg = mapper.get(username).conversations.get(ID).getParticipantList().size()+"\n";
		for (User a : mapper.get(username).conversations.get(ID).getParticipantList()) {
			msg = msg + a.getUsername() + "\n";
		}
		msg = msg + message;
		return msg;
	}
	
	
	public static void backup() {
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year  = localDate.getYear();
		int month = localDate.getMonthValue();
		int day   = localDate.getDayOfMonth();
		String datex = year + "_" + month + "_" + day;
		// Saves all accounts into backup folder.
		File makedir = new File(".\\backup\\" + datex);
		 boolean dirCreated = makedir.mkdirs();
		 
		for (String key : mapper.keySet()) {
			try {
				 if (dirCreated) {
		         FileOutputStream fileName = new FileOutputStream(makedir +"\\"+ mapper.get(key).user.getUsername()+".lanoms");
		         ObjectOutputStream out = new ObjectOutputStream(fileName);
		         out.writeObject(mapper.get(key));
		         out.close();
		         fileName.close();
		         System.out.println("Account " + mapper.get(key).user.getUsername() + " saved!");
				 }
		      } catch (IOException e) {
		         e.printStackTrace();
		      }
		}
	}
	
	
	public static void saveAccount(Account a){
		try {
			Date date = new Date();
			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int year  = localDate.getYear();
			int month = localDate.getMonthValue();
			int day   = localDate.getDayOfMonth()+1;
			String datex = year + "_" + month + "_" + day;
			// creates a Username folder inside backup folder.
			File makedir = new File(".\\backup\\" + a.user.getUsername());
			makedir.mkdirs();
			// Stores backup as date.lanoms
	         FileOutputStream fileName = new FileOutputStream(makedir + "\\" + datex+".lanoms"); 
	         ObjectOutputStream out = new ObjectOutputStream(fileName);
	         out.writeObject(a);
	         out.close();
	         fileName.close();
	         System.out.println("Account saved!");
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	}
	
	public static Account pullAccount(String username) {
		try {
	         FileInputStream fileName = new FileInputStream("backup\\" + username + ".lanoms");
	         ObjectInputStream in = new ObjectInputStream(fileName);
	         Account a = (Account) in.readObject();
	         in.close();
	         fileName.close();
	         System.out.println(a.user.getUsername() + " was found");
	         return a;
	      } catch (IOException i) {
	         //i.printStackTrace();
	    	  System.out.println("Account not found IOException");
	         return null;
	      } catch (Exception c) {
	         System.out.println("Account not found Exception");
	         //c.printStackTrace();
	         return null;
	      }
	}
}
