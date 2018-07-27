import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class CLIParserTest {
  private String[] argsWithDirectory = { "-dir", "some/path" };
  private String[] argsWithPortAndDirectory = { "-port", "7777", "-dir", "some/path" };
  private String[] argsWithNoPortAndNoDirectory = { 
                                                    "-not-port", 
                                                    "seven seven seven seven",
                                                    "not-dir",
                                                    "123/456/789"
                                                  };

  @Test 
  public void returnsTrueIfGivenDirectoryFlag() {
    CLIParser parser = new CLIParser(this.argsWithPortAndDirectory);
    assertEquals(true, parser.containsDirectoryFlag());
  }

  @Test 
  public void returnsFalseIfGivenNoDirectoryFlag() {
    CLIParser parser = new CLIParser(this.argsWithNoPortAndNoDirectory);
    assertEquals(false, parser.containsDirectoryFlag());
  }

  @Test 
  public void returnsDefaultPortNumberIfNoneIsProvided() {
    CLIParser parser = new CLIParser(this.argsWithDirectory);
    int defaultPortNumber = 9999;
    assertEquals(9999, parser.getPortNumberOrDefault(9999));
  }

  @Test 
  public void returnsPortNumberIfOneIsProvided() {
    CLIParser parser = new CLIParser(this.argsWithPortAndDirectory);
    int defaultPortNumber = 9999;
    assertEquals(7777, parser.getPortNumberOrDefault(9999));
  }
  
  @Test 
  public void returnsCorrectStoreFlag() {
    CLIParser parser = new CLIParser(this.argsWithPortAndDirectory);
    assertEquals("-dir", parser.getStoreFlag());
  }

  @Test 
  public void returnsDirectory() {
    CLIParser parser = new CLIParser(this.argsWithPortAndDirectory);
    assertEquals("some/path", parser.getDirectory());
  }

  
}