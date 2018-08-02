import java.util.Arrays;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ResponseFormatterTest {
  private final static String HTTP_VERSION = "1.1";
  private final static int STATUS_CODE = HttpStatusCode.OK;
  private final static String REASON_PHRASE = "OK";
  private final static String CONTENT_TYPE = "text/plain";
  private final static int CONTENT_LENGTH = 13;
  private final static String MESSAGE_BODY = "Hello, world!";

  private Response response = new Response.Builder(STATUS_CODE)
                                          .httpVersion(HTTP_VERSION)
                                          .reasonPhrase(REASON_PHRASE)
                                          .contentType(CONTENT_TYPE)
                                          .contentLength(CONTENT_LENGTH)
                                          .messageBody(MESSAGE_BODY)
                                          .build();

  @Test 
  public void formatsResponseWithHeaders() {
    ResponseFormatter formatter = new ResponseFormatter(response);        
<<<<<<< HEAD
    String expectedResponse = 
      "HTTP/" + HTTP_VERSION + " " + STATUS_CODE + " " + REASON_PHRASE + "\r\n" +
      ResponseHeader.CONTENT_LENGTH + ": " + CONTENT_LENGTH + "\r\n" +
      ResponseHeader.CONTENT_TYPE + ": " + CONTENT_TYPE + "\r\n" +
      "\r\n" + 
      MESSAGE_BODY;
    assertEquals(expectedResponse, formatter.formatResponse());
=======
    String expectedResponse = "HTTP/1.1 200 OK\r\n" +
                              "Content-Type: text/plain\n" + 
                              "Content-Length: 13" + "\r\n" +
                              "\r\n" + 
                              "Hello, world!";
    byte[] expectedResponseInBytes = expectedResponse.getBytes();
    assertTrue(Arrays.equals(expectedResponseInBytes, formatter.formatResponse()));
>>>>>>> Read files using byte[] rather than String, allowing responses to media other than text
  }

}
