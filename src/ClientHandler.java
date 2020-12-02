
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable, Client {

  private Socket socket;
  private ServerListener serverListener;
  private DataInputStream reader;
  private DataOutputStream writer;
  private boolean reading = true;

  public ClientHandler(Socket socket, ServerListener serverListener) throws IOException {
    this.socket = socket;
    this.serverListener = serverListener;
    reader = new DataInputStream(socket.getInputStream()); // Socket reader
    this.writer = new DataOutputStream(socket.getOutputStream()); // Socket Writer
  }

  @Override
  public void run() {
    try {
      int type;
      while (reading && (type = reader.readInt()) != 0) { // Check if still reading for socket and check message type

        if (type == MessageType.STRING_MESSAGE) { // If Message is String message
          int length = reader.readInt(); // Read Message length
          byte[] m = new byte[length];
          reader.readFully(m, 0, m.length);
          serverListener.handleMessage(new SocketMessage(null, new String(m)), this); // Send message to listener
        } 
        else if (type == MessageType.AUTH_MESSAGE) { // If message is AUTh Messgae
          int mBytes = reader.readInt(); // Username length
          byte[] m = new byte[mBytes];
          reader.readFully(m, 0, m.length);
          String username = new String(m);
          mBytes = reader.readInt(); // Paswsword Length
          m = new byte[mBytes];
          reader.readFully(m, 0, m.length);
          String password = new String(m);
          serverListener.authneticateUser(username, password, this);
        } 
        else if (type == MessageType.KEY_VALUE_MESSAGE) {
          int length = reader.readInt();
          byte[] m = new byte[length];
          reader.readFully(m, 0, m.length); // Read Message Key
          String key = new String(m);
          length = reader.readInt(); // Read Message length
          m = new byte[length];
          reader.readFully(m, 0, m.length);
          serverListener.handleMessage(new SocketMessage(key, new String(m)), this);
        } 
        else if (type == MessageType.BYTE_MESSAGE) {
          int length = reader.readInt();
          byte[] m = new byte[length];
          reader.readFully(m, 0, m.length); // Read Message Key
          String key = new String(m);
          length = reader.readInt(); // Read Message length
          m = new byte[length];
          reader.readFully(m, 0, m.length);
          serverListener.handleMessage(new SocketMessage(key, m), this);
        }
      }
    } catch (Exception e) {
      if (e instanceof EOFException) {
        serverListener.onDisconnect(socket);
      } else {
        serverListener.onException(e);
        serverListener.onDisconnect(socket);
      }
    }

  }

  @Override
  public void disconnect() throws IOException {
    reading = false;
    socket.close();

  }

  @Override
  public void send(String message) throws IOException {
    writer.writeInt(MessageType.STRING_MESSAGE); // Set message type to String
    byte[] m = message.getBytes();
    writer.writeInt(m.length); // write length of the message
    writer.write(m);
    writer.flush();
  }

  @Override
  public void send(String key, String message) throws IOException {
    writer.writeInt(MessageType.KEY_VALUE_MESSAGE); // Set message type to Key Value
    byte[] m = key.getBytes();
    writer.writeInt(m.length);
    writer.write(m);
    m = message.getBytes();
    writer.writeInt(m.length); // write length of the message
    writer.write(m);
    writer.flush();
  }

  @Override
  public void send(String key, byte[] data) throws IOException {
    writer.writeInt(MessageType.BYTE_MESSAGE); // Set message type to Key Value
    byte[] m = key.getBytes();
    writer.writeInt(m.length);
    writer.write(m);
    writer.writeInt(data.length); // write length of the message
    writer.write(data);
    writer.flush();

  }
  
  // gives the ip of the client
  public String getIp() {
	  return socket.getRemoteSocketAddress().toString();
  }
}
