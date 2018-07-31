import java.io.UnsupportedEncodingException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ResponseTest {
  private final static String HTTP_VERSION = "1.1";
  private final static String REASON_PHRASE = "OK";
  private final static String CONTENT_TYPE = "text/plain";
  private final static int CONTENT_LENGTH = 13;
  private final static String MESSAGE_BODY = "Hello, world!"; 

  private Response response = new Response.Builder(HttpStatusCode.OK)
                      .httpVersion(HTTP_VERSION)
                      .reasonPhrase(REASON_PHRASE)
                      .setHeader(ResponseHeader.CONTENT_TYPE, CONTENT_TYPE)
                      .setHeader(ResponseHeader.CONTENT_LENGTH, CONTENT_LENGTH)
                      .messageBody(MESSAGE_BODY)
                      .build();
  
  @Test 
  public void getsHTTPVersion() {
    assertEquals(HTTP_VERSION, response.getHTTPVersion());
  }

  @Test 
  public void getsValidStatusCode() {
    assertEquals(200, response.getStatusCode());
  }

  @Test
  public void getsMessageBody() {
    assertEquals(MESSAGE_BODY, response.getMessageBody());
  }

  @Test 
  public void setsHeadersWithAStringValue() {
    assertEquals(CONTENT_TYPE, response.getHeader(ResponseHeader.CONTENT_TYPE));
  }

  @Test 
  public void setsHeadersWithAnIntValue() {
    String stringifiedContentLength = Integer.toString(CONTENT_LENGTH);
    assertEquals(stringifiedContentLength, response.getHeader(ResponseHeader.CONTENT_LENGTH));
  }

  @Test
  public void getsStringifiedResponseWithCarriageReturnsAndNewLines() {
    assertEquals("HTTP/1.1 200 OK\r\n" +
                 "Content-Type: text/plain\n" + 
                 "Content-Length: 13" +  "\r\n" +
                 "\r\n" + 
                 "Hello, world!", response.toString());
  }

}