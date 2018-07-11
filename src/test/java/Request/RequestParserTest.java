import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RequestParserTest {
  private String requestString = "GET /uri/path HTTP/1.1\r\n";

  @Test
  public void parsesRequestLine() {
    RequestParser requestParser = new RequestParser(requestString);
    Request request = requestParser.generateRequest();
    assertEquals("GET", request.getMethod());
    assertEquals("/uri/path", request.getURI());
    assertEquals("1.1", request.getHTTPVersion());
  }

}
