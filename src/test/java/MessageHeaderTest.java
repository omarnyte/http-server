import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MessageHeaderTest {

  @Test 
  public void returnsTheCorrectContentLengthForText() {
    String sampleText = "Hello, world!";
    int expectedLength = 13;

    assertEquals(expectedLength, MessageHeader.determineContentLength(sampleText));
  }

}