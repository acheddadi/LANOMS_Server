import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Utility {

  public static int convertToInt(String data) {
    try {
      return Integer.parseInt(data);
    } catch (Exception e) {
      return 0;
    }
  }

  public static <T> T toMessage(byte[] data, Class<T> type) throws IOException, ClassNotFoundException {
    ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
    ObjectInputStream ois = new ObjectInputStream(inputStream);
    T obj = (T) ois.readObject();
    ois.close();
    return obj;
  }
}
