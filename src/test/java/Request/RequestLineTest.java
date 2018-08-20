import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class RequestLineTest {
  private final static String METHOD = "GET";
  private final static String URI = "/uri/path";
  private final static String VERSION = "HTTP/1.1";

  private static RequestLine requestLine;
  
  @BeforeClass
  public static void setUp() throws BadRequestException {
    String requestLineString = String.format("%s %s %s", METHOD, URI, VERSION);
    requestLine = new RequestLine(requestLineString);
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none(); 
  
  @Test 
  public void throwsBadRequestException() throws BadRequestException {
    String requestLineString = "GET\r\n";
    
    thrown.expect(BadRequestException.class);
    RequestLine requestLine = new RequestLine(requestLineString);
  }
  
  @Test 
  public void getsMethod() {
    assertEquals("GET", requestLine.getMethod());
  }

  @Test 
  public void getsURI() {
    assertEquals("/uri/path", requestLine.getURI());
  }

  @Test 
  public void getsHTTPVersion() {
    assertEquals("1.1", requestLine.getHTTPVersion());
  }

}