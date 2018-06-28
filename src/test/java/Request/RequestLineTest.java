import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RequestLineTest {
  private RequestLine requestLine = new RequestLine("GET /uri/path HTTP/1.1");
  
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