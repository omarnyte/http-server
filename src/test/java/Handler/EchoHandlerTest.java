import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EchoHandlerTest {
  private final static Handler handler = new EchoHandler();
  private final static String TIME_FORMAT = "hh:mm:ss";
  
  @Test 
  public void returnsCurrentTime(){ 
    Request request = new Request.Builder()
                                 .method("GET")
                                 .uri("/echo")
                                 .version("1.1")
                                 .build();  
                                 

    String expectedMessageBody = createExpectedMessageBody();
    Response response = handler.generateResponse(request);
    String stringifiedMessageBody = new String(response.getMessageBody());
    assertEquals(expectedMessageBody, stringifiedMessageBody);
  }

  @Test 
  public void returnsOnlyHeadersForHeadRequest() {
    Request request = new Request.Builder()
                                 .method("HEAD")
                                 .uri("/echo")
                                 .version("1.1")
                                 .build();   
                                 
    Response response = handler.generateResponse(request);
    String messageBody = new String(response.getMessageBody());
    assertEquals(200, response.getStatusCode()); 
    assertEquals("", messageBody); 
  }

  private String createExpectedMessageBody() {
    String formattedTime = getFormattedTime();
    return "Hello, world: " + formattedTime;
  }
  
  private String getFormattedTime() {
    DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
    return dateFormat.format(new Date());
  }
}
