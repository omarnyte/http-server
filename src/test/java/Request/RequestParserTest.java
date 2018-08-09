import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class RequestParserTest {
  private final static String CONTENT_LENGTH= "22";
  private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
  private final static String MESSAGE_BODY = "hello=world&hola=mundo";
  private final static String METHOD = "POST";
  private final static String URI = "/some-form.html";

  private static Request request;
  
  @BeforeClass
  public static void setUp() throws BadRequestException {
    request = createRequest();
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none(); 
  
  @Test
  public void parsesRequestLine() {
    assertEquals(METHOD, request.getMethod());
    assertEquals(URI, request.getURI());
  }

  @Test
  public void parsesHeaders() {
    assertEquals(CONTENT_TYPE, request.getHeader(MessageHeader.CONTENT_TYPE));
  }

  @Test
  public void parsesMessaegeBody() {
    HashMap<String, String> messageBody = request.getMessageBody();
    assertEquals("mundo", messageBody.get("hola"));
  }

  @Test 
  public void throwsBadRequestException() throws BadRequestException {
    String requestString = "GET\r\n";

    RequestParser requestParser = new RequestParser(new BufferedReader(new StringReader(requestString)));
    
    thrown.expect(BadRequestException.class);
    Request request = requestParser.generateRequest();
  }

  private static Request createRequest() throws BadRequestException {
    String requestString = createRequestString();
    BufferedReader reader = createReaderFromString(requestString);
    RequestParser requestParser = new RequestParser(reader);
    return requestParser.generateRequest();
  }

  private static String createRequestString() {
    return METHOD + " " + URI + " " + "HTTP/1.1\r\n" + 
           MessageHeader.CONTENT_TYPE + ": " + CONTENT_TYPE + "\r\n" +
           MessageHeader.CONTENT_LENGTH + ": " + CONTENT_LENGTH + "\r\n" +
           "\r\n" +
           MESSAGE_BODY;
  }

  private static BufferedReader createReaderFromString(String requestString) {
    InputStream stream = new ByteArrayInputStream(requestString.getBytes());
    return new BufferedReader(new InputStreamReader(stream));
  }

}
