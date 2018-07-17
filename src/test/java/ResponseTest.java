import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ResponseTest {
  private Response response = new Response.Builder()
                                          .httpVersion("1.1")
                                          .statusCode(200)
                                          .reasonPhrase("OK")
                                          .messageBody("Hello, world!")
                                          .build();
  
  @Test 
  public void getsHTTPVersion() {
    assertEquals("1.1", response.getHTTPVersion());
  }

  @Test 
  public void getsValidStatusCode() {
    assertEquals(200, response.getStatusCode());
  }

  @Test 
  public void getsReasonPhrase() {
    assertEquals("OK", response.getReasonPhrase());
  }

  @Test
  public void setsMessageBody() {
    assertEquals("Hello, world!", response.getMessageBody());
  }

  @Test
  public void getsStringifiedResponseWithCarriageReturnsAndNewLines() {
    assertEquals("HTTP/1.1 200 OK\r\n" +
                 "\r\n" + 
                 "Hello, world!", response.toString());
  }

}