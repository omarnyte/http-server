import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RequestTest {
  private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
  private final static String CONTENT_LENGTH = "application/x-www-form-urlencoded";
  private final static String MESSAGE_BODY_KEY = "hello";
  private final static String MESSAGE_BODY_VAL = "world";
  
  private Request request = new Request.Builder()
                          .method("GET")
                          .uri("/uri/path")
                          .version("1.1")
                          .setHeader(MessageHeader.CONTENT_TYPE, CONTENT_TYPE)
                          .addMessageBodyKeyVal(MESSAGE_BODY_KEY, MESSAGE_BODY_VAL)
                          .build();

  @Test 
  public void getsMethod() {
    assertEquals("GET", request.getMethod());
  }

  @Test 
  public void getsURI() {
    assertEquals("/uri/path", request.getURI());
  }

  @Test 
  public void getsHTTPVersion() {
    assertEquals("1.1", request.getHTTPVersion());
  }

  @Test 
  public void getsHeadersHashMap() {
    HashMap<String, String> headers = request.getHeaders();
    assertEquals(CONTENT_TYPE, headers.get(MessageHeader.CONTENT_TYPE));
  }

  @Test 
  public void setsHeadersWithAStringValue() {
    assertEquals(CONTENT_TYPE, request.getHeader(MessageHeader.CONTENT_TYPE));
  }

  @Test 
  public void getsMessageBodyHashMap() {
    HashMap<String, String> headers = request.getMessageBody();
    assertEquals(MESSAGE_BODY_VAL, headers.get(MESSAGE_BODY_KEY));
  }
  
}
