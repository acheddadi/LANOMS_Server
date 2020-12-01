public class SocketMessage {
  private String key;
  private String data;
  private byte[] byteData;

  public SocketMessage(String key, String data) {
    this.key = key;
    this.data = data;
  }

  public SocketMessage(String key, byte[] data) {
    this.key = key;
    this.byteData = data;
  }

  public SocketMessage(String key, String data, byte[] byteData) {
    this.key = key;
    this.data = data;
    this.byteData = byteData;
  }
  
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public byte[] getByteData() {
    return byteData;
  }

  public void setByteData(byte[] byteData) {
    this.byteData = byteData;
  }

}
