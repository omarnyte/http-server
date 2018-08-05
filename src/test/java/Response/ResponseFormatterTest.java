import static org.junit.Assert.assertEquals;
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
                      .setHeader(ResponseHeader.CONTENT_LENGTH, CONTENT_LENGTH)
                      .setHeader(ResponseHeader.CONTENT_TYPE, CONTENT_TYPE)
                      .messageBody(MESSAGE_BODY)
                      .build();

  @Test 
  public void formatsResponseWithHeaders() {
    ResponseFormatter formatter = new ResponseFormatter(response);        
    String expectedResponse = 
      "HTTP/" + HTTP_VERSION + " " + STATUS_CODE + " " + REASON_PHRASE + "\r\n" +
      ResponseHeader.CONTENT_LENGTH + ": " + CONTENT_LENGTH + "\r\n" +
      ResponseHeader.CONTENT_TYPE + ": " + CONTENT_TYPE + "\r\n" +
      "\r\n" + 
      MESSAGE_BODY;
    String stringifiedFormattedResponse = new String(formatter.formatResponse());
    assertEquals(expectedResponse, stringifiedFormattedResponse);
  }

}
