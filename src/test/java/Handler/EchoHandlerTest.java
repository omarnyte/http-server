import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class EchoHandlerTest {
  @Test 
  public void returnsCurrentTime(){ 
    Request request = new Request.Builder()
                                 .method("GET")
                                 .uri("/echo")
                                 .version("1.1")
                                 .build();  
                                 
    String timeFormat = "hh:mm:ss";
    DateFormat dateFormat = new SimpleDateFormat(timeFormat);
    String formattedTime = dateFormat.format(new Date());
    String expectedMessageBody = "Hello, world: " + formattedTime;
                             
    EchoHandler handler = new EchoHandler();
    Response response = handler.generateResponse(request);

    byte[] expectedMessageBodyInBytes = expectedMessageBody.getBytes();
    assertTrue(Arrays.equals(expectedMessageBodyInBytes, response.getMessageBody()));
  }
}
