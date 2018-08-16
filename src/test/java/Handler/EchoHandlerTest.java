import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EchoHandlerTest {
  private final static Handler handler = new EchoHandler();
  private final static String TIME_PATTERN = "hh:mm:ss";
  
  @Test 
  public void returnsCurrentTime(){ 
    Request request = TestUtil.buildRequestToUri("GET", "/echo");
    String expectedMessageBody = createExpectedMessageBody();
    Response response = handler.generateResponse(request);
    String stringifiedMessageBody = new String(response.getMessageBody());
    assertEquals(expectedMessageBody, stringifiedMessageBody);
  }

  @Test 
  public void returnsOnlyHeadersForHeadRequest() {
    Request request = TestUtil.buildRequestToUri("HEAD", "/echo");
    Response response = handler.generateResponse(request);
    String messageBody = new String(response.getMessageBody());
    assertEquals(HttpStatusCode.OK, response.getStatusCode()); 
    assertEquals("", messageBody); 
  }

  private String createExpectedMessageBody() {
    String formattedTime = TestUtil.getFormattedTime(TIME_PATTERN);
    return "Hello, world: " + formattedTime;
  }
  
}
