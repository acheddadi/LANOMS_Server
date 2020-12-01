import java.io.Serializable;

public class MakeMessage implements Serializable {
  
  private static final long serialVersionUID = 6990096704585492072L;
  private int id;
  private String username;
  private String messgae;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMessgae() {
    return messgae;
  }

  public void setMessgae(String messgae) {
    this.messgae = messgae;
  }

}
