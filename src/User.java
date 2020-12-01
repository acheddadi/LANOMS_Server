import java.io.Serializable;

public class User implements Serializable {
	private String username;
	private String password;
	private String name;
	private String department;
	private String email;
	
	
	User(String username, String password, String name, String department, String email){
		this.username= username;
		this.password = password;
		this.name = name;
		this.department = department;
		this.email = email;
	}
	
	// Setters
	public void setPassword(String s) {
		this.password = s;
	}
	
	public void setUsername(String s) {
		this.username = s;
	}
	
	public void setName(String s) {
		this.name = s;
	}
	
	
	// Getters
	public int verifyPassword(String s) {
		if (this.password == null) {
			setPassword(s);
			return 1;
		}
		if (this.password == s) {
			return 1;
		}
		else return 0;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
	    return "\nUsername: " + this.getUsername() + "	Password: " + this.password + "	Name: " + this.name;
	}
	
	
}
