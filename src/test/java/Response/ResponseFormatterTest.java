import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ResponseFormatterTest {
  private final static String HTTP_VERSION = "1.1";
  private final static int STATUS_CODE = HttpStatusCode.OK;
  private final static String REASON_PHRASE = "OK";
  private final static String CONTENT_TYPE = "text/plain";
  private final static int CONTENT_LENGTH = 13;
  private final static String MESSAGE_BODY = "Hello, world!";

  @Test 
  public void formatsResponseWithMessage() {
    Response response = new Response.Builder(STATUS_CODE)
                      .httpVersion(HTTP_VERSION)
                      .reasonPhrase(REASON_PHRASE)
                      .setHeader(MessageHeader.CONTENT_LENGTH, CONTENT_LENGTH)
                      .setHeader(MessageHeader.CONTENT_TYPE, CONTENT_TYPE)
                      .messageBody(MESSAGE_BODY)
                      .build();
    ResponseFormatter formatter = new ResponseFormatter(response);   

    String expectedResponse = createExpectedResponseWithMessageBodyAndHeaders();
    String stringifiedFormattedResp = new String(formatter.formatResponse());
    assertEquals(expectedResponse, stringifiedFormattedResp);
  }

  @Test 
  public void formatsResponseWithoutMessage() {
    Response response = new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED)
                                    .build();
    ResponseFormatter formatter = new ResponseFormatter(response);        

    String expectedResponse = createExpectedResponseWithNoMessageBody();
    String stringifiedFormattedResp = new String(formatter.formatResponse());
    assertEquals(expectedResponse, stringifiedFormattedResp);
  }

  private String createExpectedResponseWithMessageBodyAndHeaders() {
    return "HTTP/" + HTTP_VERSION + " " + STATUS_CODE + " " + REASON_PHRASE + "\r\n" +
      MessageHeader.CONTENT_LENGTH + ": " + CONTENT_LENGTH + "\r\n" +
      MessageHeader.CONTENT_TYPE + ": " + CONTENT_TYPE + "\r\n" +
      "\r\n" + 
      MESSAGE_BODY;
  }

  private String createExpectedResponseWithNoMessageBody() {
    int statusCode = HttpStatusCode.METHOD_NOT_ALLOWED;
    return "HTTP/" + HTTP_VERSION + " " + statusCode + " " + HttpStatusCode.getReasonPhrase(statusCode) + "\r\n" +
    "\r\n";
  }

}
