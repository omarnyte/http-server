import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test;

public class LoggerTest {
  private final static String DATE_TIME_PATTERN = "yyyymmddhhmmss";
  private final static String TEMP_DIRECTORY_PATH = System.getProperty("user.dir") + "/src/test/java/LoggerTestDirectory";

  private static File logFile;
  private static String logFileName;
  private static Logger logger;
  
  @BeforeClass
  public static void setUp() throws IOException, LoggerException {
    TempDirectory temp = new TempDirectory(TEMP_DIRECTORY_PATH);
    
    logger = new Logger(TEMP_DIRECTORY_PATH, DATE_TIME_PATTERN);
    logFileName = TestUtil.getFormattedTime(DATE_TIME_PATTERN) + ".txt";
    logFile = logger.createLogFile(); 
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();  

  @Test 
  public void throwsLoggerExceptionWhenLoggerCannotBeStarted() throws LoggerException {
    String invalidPathName = "some/invalid/path/name";
    thrown.expect(LoggerException.class);
    Logger logger = new Logger(invalidPathName, DATE_TIME_PATTERN);
    logFile = logger.createLogFile();
  }
  
  @Test
  public void createsNewLogFileWithTimeStampAsName() {    
    System.out.println("first test");
    assertTrue(logFile.exists());
  }  

  @Test 
  public void logsEntryToLogFile() throws IOException, LoggerException {
    System.out.println("second test");
    String entry = "This line will be written in the log.";
    logger.logEntry(entry);
    byte[] logBytes = readFile(logFileName);
    String logFileContent = new String(logBytes);
    assertTrue(logFileContent.contains(entry));
  }

  @Test   
  public void logsRequest() throws IOException {
    String entryType = "REQUEST";
    String sampleRequestString = "GET /some/uri/to/be/logged HTTP/1/1";
    logger.logEntry(entryType, sampleRequestString);
    
    byte[] logBytes = readFile(logFileName);
    String logFileContent = new String(logBytes);
    String expectedLogEntry = "[INFO]: REQUEST\n" + sampleRequestString;
    assertTrue(logFileContent.contains(expectedLogEntry));
  }

  @Test 
  public void logsResponse() throws IOException {
    String entryType = "RESPONSE";
    String sampleResponseString = "HTTP/1.1 200 OK\n" +
                                  "Content-Length: 30\n" +
                                  "Content-Type: text/html\n" +
    
                                  "<a href=\"/hello/my\">my</a><br>";
    logger.logEntry(entryType,sampleResponseString);
        
    byte[] logBytes = readFile(logFileName);
    String logFileContent = new String(logBytes);
    String expectedLogEntry = "[INFO]: RESPONSE\n" + sampleResponseString;
    assertTrue(logFileContent.contains(expectedLogEntry));
  }

  public byte[] readFile(String fileName) {
    Path filePath = Paths.get(TEMP_DIRECTORY_PATH + "/" + fileName);
    byte[] fileBytes = null;
    try {
      fileBytes = Files.readAllBytes(filePath);
    } catch (IOException e) {
      System.err.println("Could not read file: " + fileName);
      e.printStackTrace();
    }
    return fileBytes;
  }

  @AfterClass
  public static void tearDown() {
    File tempDirectory = new File(TEMP_DIRECTORY_PATH);
    TempDirectory.deleteSubdirectories(tempDirectory);
  } 

}
