import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ResponseTest {

  private Response goodResponse = new Response();
  private Response badResponse = new Response();
  
  @Test 
  public void setsHTTPVersion() {
    goodResponse.setHTTPVersion("1.1");
    assertEquals("1.1", goodResponse.getHTTPVersion());
  }

  @Test 
  public void setsValidStatusCode() {
    goodResponse.setStatusCode(404);
    assertEquals(404, goodResponse.getStatusCode());
  }

  @Test 
  public void setsReasonPhrase() {
    goodResponse.setReasonPhrase("OK");
    assertEquals("OK", goodResponse.getReasonPhrase());
  }

  @Test
  public void getsStatusLineWithReasonPhrase() {
    goodResponse.setHTTPVersion("1.1");
    goodResponse.setStatusCode(200);
    goodResponse.setReasonPhrase("OK");
    assertEquals("HTTP/1.1 200 OK\r\n", goodResponse.getStatusLine());
  }

  @Test
  public void getsStatusLineWithNoReasonPhrase() {
    goodResponse.setHTTPVersion("1.1");
    goodResponse.setStatusCode(200);
    assertEquals("HTTP/1.1 200 \r\n", goodResponse.getStatusLine());
  }

  @Test
  public void setsMessageBody() {
    goodResponse.setMessageBody("Hello, world!");
    assertEquals("Hello, world!", goodResponse.getMessageBody());
  }

  @Test
  public void getsStringifiedResponseWithCarriageReturnsAndNewLines() {
    goodResponse.setHTTPVersion("1.1");
    goodResponse.setStatusCode(200);
    goodResponse.setMessageBody("Hello, world!");
    assertEquals("HTTP/1.1 200 \r\n" +
                 "\r\n" + 
                 "Hello, world!", goodResponse.toString());
  }

}