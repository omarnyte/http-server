import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test;

public class CLIParserTest {
  private String[] argsWithDirectory = { "-dir", "some/path" };
  private String[] argsWithPortAndDirectory = { "-port", "7777", "-dir", "some/path" };
  private String[] argsWithNoPortAndNoDirectory = { "-not-port", 
                                                    "seven seven seven seven",
                                                    "not-dir",
                                                    "123/456/789" };
  private String[] emptyArgs = { };

  @Rule
  public ExpectedException thrown = ExpectedException.none();  
  
  @Test 
  public void returnsDefaultPortNumberIfNoneIsProvided() throws UnsupportedFlagException {
    CLIParser parser = new CLIParser(this.argsWithDirectory);
    int defaultPortNumber = 9999;
    assertEquals(9999, parser.getPortNumberOrDefault(9999));
  }

  @Test 
  public void returnsPortNumberIfOneIsProvided() throws UnsupportedFlagException {
    CLIParser parser = new CLIParser(this.argsWithPortAndDirectory);
    int defaultPortNumber = 9999;
    assertEquals(7777, parser.getPortNumberOrDefault(9999));
  }
  
  @Test 
  public void throwsMissingFlagExceptionWhenNoStoreProvided() throws MissingFlagException, UnsupportedFlagException {
    CLIParser parser = new CLIParser(this.emptyArgs);

    thrown.expect(MissingFlagException.class);
    String storeFlag = parser.getStoreFlag();
  }
  
  @Test 
  public void returnsCorrectStoreFlag() throws MissingFlagException, UnsupportedFlagException {
    CLIParser parser = new CLIParser(this.argsWithPortAndDirectory);
    assertEquals("-dir", parser.getStoreFlag());
  }

  @Test 
  public void returnsDirectory() throws UnsupportedFlagException {
    CLIParser parser = new CLIParser(this.argsWithPortAndDirectory);
    assertEquals("some/path", parser.getDirectory());
  }
  
}
