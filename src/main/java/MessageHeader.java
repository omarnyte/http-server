import java.io.UnsupportedEncodingException;

public class MessageHeader {
  public final static String CONTENT_LENGTH = "Content-Length";
  public final static String CONTENT_TYPE = "Content-Type";
  public final static String LOCATION = "Location";
  
  public static int determineContentLength(String messageBody) {
    int length = 0;
    try {
      length = messageBody.getBytes("UTF-8").length;
    } catch (UnsupportedEncodingException e) {
      System.err.println(e.getMessage());
    }

    return length;
  }
  
}