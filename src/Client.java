import java.io.IOException;

public interface Client {
  public void disconnect() throws IOException ;
  public void send(String message)  throws IOException ;
  public void send(String key, String message)  throws IOException ;
  public void send(String key, byte[] data)  throws IOException ;
  public String getIp();
}
