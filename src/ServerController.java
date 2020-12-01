import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class ServerController implements ServerListener {

	// static HashMap<String, Account> account = new HashMap<String, Account>();

	static ArrayList<Account> account = new ArrayList<Account>();
	static HashMap<String, Account> mapper = new HashMap<String, Account>();
	
	static HashMap<String, String> loggedIn = new HashMap<String, String>();

	public static void main(String[] args) {
		// Make accounts (can make it to load existing accounts in future).
		makeAccounts();
		makeConversation(0, 1);
		makeConversation(1, 2);
		makeMessage("ali", "Hello Nik", 0);
		makeMessage("nik", "Hi Ali", 0);

		makeConversation(2, 1);
		makeConversation(1, 2);
		// User Index 1 adds index 0 to convo
		//account.get(1).addToConvo(1, account.get(0));
		
		ServerController controller = new ServerController();
		controller.init();
		backup();
	}

	public void init(){

		SocketServer socketServer = new SocketServer(9009, this);
		socketServer.start();
		System.out.println("Socket Server Started");
	}

	public static String getUserCount() {
		// Input - None
		// Output - Number of users in the system
		return account.size() + "";
	}

	public static String getUserInfo(int i) {
		// Input: Index of array (i) for user
		// Output: User information as Username \n Message \n Status \n Department \n
		// Email
		return account.get(i).user.getUsername() + "\n" + account.get(i).user.getName() + "\n" + account.get(i).uSettings.getMessage() + "\n"
				+ account.get(i).uSettings.getStatus() + "\n" + account.get(i).user.getDepartment() + "\n"
				+ account.get(i).user.getEmail() + "\n";
	}

	public byte[] getUserDisplayPicture(int i) {
		return account.get(i).uSettings.getIMAGE();
	}

	public static String getConversationCount(String username) {
		// Input: Index of account array
		// Output: Size of conversation
		return mapper.get(username).conversations.size() + "";
	}

	public static String getMessageCount(String username, int j) {
		// Input: i = index of account array
		// j = index of conversation array
		// Output: number of messages in the conversation
		return mapper.get(username).conversations.get(j).getChatLog().size() + "";
	}

	public static String getMessage(String username, int j, int k) {
		// Input: username = username
		// j = index of conversation array
		// k = index of message array
		// Output: The message as NAME \n MESSAGE

		return mapper.get(username).conversations.get(j).getChatLog().get(k).getSender() + "\n"
				+ mapper.get(username).conversations.get(j).getChatLog().get(k).getMessage();
	}

	// Add additional accounts
	public static void makeAccounts() {
		// Existing accounts

		User ali = new User("ali", "test", "Ali", "CS", "Ali@email.com");
		Account a = new Account(ali);
		account.add(a);
		mapper.put(a.user.getUsername(), a);

		User nik = new User("nik", "test", "Nik", "CS", "Nik@email.com");
		Account n = new Account(nik);
		account.add(n);
		mapper.put(n.user.getUsername(), n);

		User shree = new User("shree", "test", "Shree", "CS", "Shree@email.com");
		Account s = new Account(shree);
		account.add(s);
		mapper.put(s.user.getUsername(), s);
	}

	public static void makeConversation(int i, int j) { // make conversations in server
		// Input = User1 index, User 2 index
		Conversation cov = new Conversation(account.get(i).user, account.get(j).user);
		account.get(i).conversations.add(cov);
		account.get(j).conversations.add(cov);
	}

	public static int checkValidLogin(String name, String pass) {
		// Input: Username Password
		// Output: String true or false. true = Valid pass. false = Invalid pass.
		System.out.println("Login with: " + name + " " + pass);
		
		if (mapper.get(name) == null) return User.INVALID_USER;
		return (mapper.get(name).user.verifyPassword(pass)); // checks if password is valid
	}

	public static Message makeMessage(String username, String message, int ID) {
		// Input: String Username, String message, int Index of conversation
		// Saves to the server.
		// Output = Message object

		Message mes = new Message(mapper.get(username).user, message);
		mapper.get(username).conversations.get(ID).addMessage(mes, mapper.get(username).user);
		return mes;
	}
	
	public static void setStatus(String username, int stat) {
		switch(stat) {
		case 1:
			mapper.get(username).uSettings.setStatus("Online");
		case 2:
			mapper.get(username).uSettings.setStatus("Busy");
		case 3:
			mapper.get(username).uSettings.setStatus("Away");
		case 4:
			mapper.get(username).uSettings.setStatus("Offline");
		}
	}
	
	public static String getAllStatus() {
		// Sends string "UserID \n Status\n ... "
		String stat = "";
		for (int i = 0; i < account.size(); i++)
		{
			stat = stat + i + "\n" + account.get(i).uSettings.getStatus() + "\n";
		}
		return stat;
	}
	
	
	
	
	public static void backup() {
		// Creates a backup folder with all user accounts
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year  = localDate.getYear();
		int month = localDate.getMonthValue();
		int day   = localDate.getDayOfMonth();
		String datex = year + "_" + month + "_" + day;
		// Saves all accounts into backup folder.
		File makedir = new File(".\\backup\\" + datex);
		File makedir2 = new File(".\\backup\\lastSave");
		boolean dirCreated = makedir.mkdirs();
		makedir2.mkdirs();
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
			
			try {
		         FileOutputStream fileName = new FileOutputStream(makedir2 +"\\"+ mapper.get(key).user.getUsername()+".lanoms");
		         ObjectOutputStream out = new ObjectOutputStream(fileName);
		         out.writeObject(mapper.get(key));
		         out.close();
		         fileName.close();
		         System.out.println("Account " + mapper.get(key).user.getUsername() + " Last Backup Saved!");
		      } catch (IOException e) {
		         e.printStackTrace();
		      }
		}
	}
	
	
	public static void saveAccount(Account a){
		// Saves account to backup//username//Date_of_Save
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
		// Pulls saved account from username (Need to move the file to backup folder). 
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

	@Override
	public void handleMessage(SocketMessage data, Client client) {
		try {

			if (data.getKey() != null) {
				String res = null;
				String[] parts;
				switch (data.getKey()) {
					case "USER_COUNT":
						res = getUserCount();
						break;
					case "USER_INFO":
						res = getUserInfo(Utility.convertToInt(data.getData()));
						break;
					case "CONV_COUNT":
						res = getConversationCount(data.getData());
						break;
					case "MSG_COUNT":
						parts = data.getData().split("\n");
						res = getMessageCount(parts[0], Utility.convertToInt(parts[1]));
						break;
					case "GET_MSG":
						parts = data.getData().split("\n");
						res = getMessage(parts[0], Utility.convertToInt(parts[1]),
								Utility.convertToInt(parts[2]));
						break;
					case "MAKE_CONV":
						parts = data.getData().split("\n");
						makeConversation(Utility.convertToInt(parts[0]), Utility.convertToInt(parts[1]));
						break;
					case "MAKE_MESSAGE":
						MakeMessage makeMessage = Utility.toMessage(data.getByteData(), MakeMessage.class);
						makeMessage(makeMessage.getUsername(), makeMessage.getMessgae(), makeMessage.getId());
						break;
					case "ALL_STATUS":
						res = getAllStatus();
						break;
					case "DISPLAY_PICTURE":
						res = getUserDisplayPicture(Utility.convertToInt(data.getData())) + "";
						break;
					case "USER_AUTH":
						parts = data.getData().split("\n");
                        res = checkValidLogin(parts[0], parts[1]) + "";
                        break;
					default:
						System.out.println("Result Key not handled: " + data.getKey());
						return;

				}
				if (res != null) {
					client.send(data.getKey(), res);
				}

			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			;
		}

	}

	@Override
	public void onAccept(Socket socket) {
		System.out.println("User Connected from: " + socket.getRemoteSocketAddress().toString());

	}

	@Override
	public void onDisconnect(Socket socket) {
		System.out.println("User Disconnected from: " + socket.getRemoteSocketAddress().toString());

	}

	@Override
	public void onException(Exception e) {
		e.printStackTrace();

	}

	@Override
	public void authneticateUser(String username, String password, Client client) {

		try {
			if (checkValidLogin(username, password) == 1) {
				client.send("AUTH", 1 + "");
			} 
			else {
				client.send("AUTH", 0 + "");
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
