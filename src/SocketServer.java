import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {

  private int port;
  private boolean isAccept = true;
  private ServerListener serverListener;
  private ServerSocket serverSocket;
  private List<ClientHandler> clients;

  public SocketServer(int port, ServerListener serverListener) {
    this.port = port;
    this.serverListener = serverListener;
    this.clients = new ArrayList<>();
  }

  public void start() {
    Thread th = new Thread(() -> {
      try {
        serverSocket = new ServerSocket(port);
        while (isAccept) {
          Socket socket = serverSocket.accept(); // Accept New Connection
          serverListener.onAccept(socket);
          ClientHandler clientHandler = new ClientHandler(socket, serverListener);
          clients.add(clientHandler);
          Thread thread = new Thread(clientHandler); // Create Thread for connection
          thread.start();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    });
    th.start();

  }

  /**
   * Stop Accepting incoming Connections
   */
  public void stop() {
    isAccept = false;

  }
}
