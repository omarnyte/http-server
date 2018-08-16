import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogFormatterTest {
  private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
  private final static String CONTENT_LENGTH = "13";
  private final static String MESSAGE_BODY = "hello, world!";
  private final static String MESSAGE_BODY_KEY = "hello";
  private final static String MESSAGE_BODY_VAL = "world";
  private final static String METHOD = "GET";
  private final static String REASON_PHRASE = "OK";
  private final static String URI = "/uri/path";
  private final static String VERSION = "1.1";

  private static LogFormatter logFormatter; 

  @BeforeClass
  public static void setUp() {
    logFormatter = new LogFormatter();
  }
  
  @Test 
  public void logsRequestWithoutMessageBody() {
    Request request = buildRequest();

    String expectedFormattedRequest = createdExpectedFormattedRequest();
    String requestFormattedForLogger = logFormatter.formatRequest(request);
    assertEquals(expectedFormattedRequest, requestFormattedForLogger);
  }

  @Test 
  public void logsResponseWithoutMessageBody() {
    Response response = buildResponse();

    String expectedFormattedResponse = createdExpectedFormattedResponse();
    String responseFormattedForLogger = logFormatter.formatResponse(response);
    assertEquals(expectedFormattedResponse, responseFormattedForLogger);
  }

  private static Request buildRequest() {
    return new Request.Builder()
                      .method(METHOD)
                      .uri(URI)
                      .version(VERSION)
                      .setHeader(MessageHeader.CONTENT_TYPE, CONTENT_TYPE)
                      .setHeader(MessageHeader.CONTENT_LENGTH, CONTENT_LENGTH)
                      .addMessageBodyKeyVal(MESSAGE_BODY_KEY, MESSAGE_BODY_VAL)
                      .build();
  }

  private static Response buildResponse() {
    return new Response.Builder(HttpStatusCode.OK)
                       .httpVersion(VERSION)
                       .reasonPhrase(REASON_PHRASE)
                       .setHeader(MessageHeader.CONTENT_TYPE, CONTENT_TYPE)
                       .setHeader(MessageHeader.CONTENT_LENGTH, CONTENT_LENGTH)
                       .messageBody(MESSAGE_BODY)
                       .build();
  }

  private static String createdExpectedFormattedRequest() {
    return "[INFO]: REQUEST\n" + 
           "  Request Line: " + METHOD + " " + URI + " HTTP/" + VERSION + "\n" +
           "  Request Headers:\n" + 
           "  " + MessageHeader.CONTENT_LENGTH + ": " + CONTENT_LENGTH + "\n" +
           "  " + MessageHeader.CONTENT_TYPE + ": " + CONTENT_TYPE + "\n";
  }

  private static String createdExpectedFormattedResponse() {
    String reasonPhrase = HttpStatusCode.getReasonPhrase(HttpStatusCode.OK);
    return "[INFO]: RESPONSE\n" + 
           "  Status Line: " + "HTTP/" + VERSION + " " + HttpStatusCode.OK + " " + reasonPhrase + "\n" + 
           "  Response Headers:\n" + 
           "  " + MessageHeader.CONTENT_LENGTH + ": " + CONTENT_LENGTH + "\n" +
           "  " + MessageHeader.CONTENT_TYPE + ": " + CONTENT_TYPE + "\n";
  }

}
