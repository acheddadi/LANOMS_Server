import java.net.Socket;

public interface ServerListener {

  public void handleMessage(SocketMessage data, Client client);

  public void onAccept(Socket socket);

  public void onDisconnect(Socket socket);

  public void onException(Exception e);

  public void authneticateUser(String username, String password, Client client);

}
