import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class RequestParserTest {
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();  

  @Test
  public void parsesRequestLine() throws BadRequestException {
    String requestString = "GET /uri/path HTTP/1.1\r\n";
    RequestParser requestParser = new RequestParser(requestString);
    Request request = requestParser.generateRequest();
    assertEquals("GET", request.getMethod());
    assertEquals("/uri/path", request.getURI());
    assertEquals("1.1", request.getHTTPVersion());
  }

  @Test 
  public void throwsBadRequestException() throws BadRequestException {
    String requestString = "GET\r\n";
    RequestParser requestParser = new RequestParser(requestString);
    
    thrown.expect(BadRequestException.class);
    Request request = requestParser.generateRequest();
  }

}
