import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ResponseConstructorTest {

  @Test 
  public void builds405Response() {
    int statusCode = 405;
    String reasonPhrase = "Method Not Allowed";
    Response response = new ResponseConstructor(statusCode).constructResponse();

    String expectedRespString = "HTTP/1.1 405 Method Not Allowed\r\n" +
                                "\r\n";
    assertEquals(expectedRespString, response.toString());
  }
}