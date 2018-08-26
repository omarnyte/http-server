import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class RequestTest {
  private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
  private final static String CONTENT_LENGTH = "";
  private final static String MESSAGE_BODY_KEY = "hello";
  private final static String MESSAGE_BODY_VAL = "13";
  private final static String METHOD = "GET";
  private final static String URI = "/uri/path";
  private final static String VERSION = "1.1";
  
  private static HashMap<String, String> headers;
  private static HashMap<String, String> messageBody;
  private static Request request;
                  
  @BeforeClass 
  public static void setUp() {
    headers = setHeaders();
    messageBody = setMessageBody();
    request = new Request.Builder()
                         .method(METHOD)
                         .uri(URI)
                         .version(VERSION)
                         .headers(headers)
                         .messageBody(messageBody)
                         .build();
  }
                          
  @Test 
  public void getsMethod() {
    assertEquals(METHOD, request.getMethod());
  }

  @Test 
  public void getsURI() {
    assertEquals(URI, request.getURI());
  }

  @Test 
  public void getsHTTPVersion() {
    assertEquals(VERSION, request.getHTTPVersion());
  }

  @Test 
  public void getsHeadersHashMap() {
    HashMap<String, String> expectedHeaders = headers;
    HashMap<String, String> actualHeaders = request.getHeaders();
    assertEquals(expectedHeaders, actualHeaders);
  }

  @Test 
  public void setsHeadersWithAStringValue() {
    assertEquals(CONTENT_TYPE, request.getHeader(MessageHeader.CONTENT_TYPE));
  }

  @Test 
  public void getsMessageBodyHashMap() {
    HashMap<String, String> expectedMessageBody = messageBody;
    HashMap<String, String> actualMessageBody = request.getMessageBody();
    assertEquals(expectedMessageBody, actualMessageBody);
  }

  private static HashMap<String, String> setHeaders() {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put(MessageHeader.CONTENT_TYPE, CONTENT_TYPE);
    headers.put(MessageHeader.CONTENT_LENGTH, CONTENT_LENGTH);
    return headers;
  }

  private static HashMap<String, String> setMessageBody() {
    HashMap<String, String> messageBody = new HashMap<String, String>();
    messageBody.put(MESSAGE_BODY_KEY, MESSAGE_BODY_VAL);
    return messageBody;
  }
  
}
