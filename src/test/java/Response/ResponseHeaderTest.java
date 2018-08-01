import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ResponseHeaderTest {

  @Test 
  public void returnsTheCorrectContentLengthForText() {
    String sampleText = "Hello, world!";
    int expectedLength = 13;

    assertEquals(expectedLength, ResponseHeader.determineContentLength(sampleText));
  }

}