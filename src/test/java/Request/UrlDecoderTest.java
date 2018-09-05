import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UrlDecoderTest {
  private static UrlDecoder urlDecoder = new UrlDecoder();
  
  @Test 
  public void doesNotParseNonEncodedSubstringMatchingRegEx() {
    String encodedString = "%99+should+not+be+decoded"; 
    String expectedString = "%99 should not be decoded";
    String decodedString = urlDecoder.decodeString(encodedString);
    assertEquals(expectedString, decodedString);
  }

  @Test 
  public void parsesSpacesEncodedWithPercent20() {
    String encodedString = "string%20with%20encoded%20spaces"; 
    String expectedString = "string with encoded spaces";
    String decodedString = urlDecoder.decodeString(encodedString);
    assertEquals(expectedString, decodedString);
  }
  
  @Test 
  public void parsesSpacesEncodedWithPlusSign() {
    String encodedString = "string+with+encoded+spaces"; 
    String expectedString = "string with encoded spaces";
    String decodedString = urlDecoder.decodeString(encodedString);
    assertEquals(expectedString, decodedString);
  }
  
  @Test 
  public void parsesEncodedString() {
    String encodedString = "%5Bvalue%20with%20encoded%20characters%5D";
    String expectedString = "[value with encoded characters]";
    String decodedString = urlDecoder.decodeString(encodedString);
    assertEquals(expectedString, decodedString);
  }
}