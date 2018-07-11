import static org.junit.Assert.assertEquals;
<<<<<<< HEAD
=======
import org.junit.Before;
>>>>>>> c12ea36cc1330e3b1f8f4da75e64eb2de36c41ac
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
