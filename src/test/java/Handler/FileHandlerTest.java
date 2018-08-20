import java.io.IOException; 
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Test; 
 
public class FileHandlerTest { 
  private final static String NONEXISTENT_FILE_URI = "/does-not-exist.txt";
  private final static String TEXT_FILE_CONTENT = "This is a sample text file.";
  private final static String TEXT_FILE_URI = "/text-file.txt";
  
  private static Handler handler;
  private Request requestToExistingFile = TestUtil.buildRequestToUri("GET", TEXT_FILE_URI);

  @BeforeClass
  public static void setUp() throws IOException, NonexistentDirectoryException {
    String tempDirectoryPath = System.getProperty("user.dir") + "/src/test/java/Handler/TestDirectory";

    TempDirectory temp = new TempDirectory(tempDirectoryPath);
    temp.createFileWithContent(TEXT_FILE_URI, TEXT_FILE_CONTENT);

    Directory directory = new Directory(tempDirectoryPath); 
    handler = new FileHandler(directory); 
  }
  
  @Test  
  public void returns404IfFileDoesNotExist() {  
    Request request = TestUtil.buildRequestToUri("GET", NONEXISTENT_FILE_URI);
    Response response = handler.generateResponse(request); 
    assertEquals(404, response.getStatusCode()); 
  } 

  @Test  
  public void returns200IfFileExists() { 
    Response response = handler.generateResponse(this.requestToExistingFile); 
    assertEquals(200, response.getStatusCode()); 
  } 

  @Test 
  public void returnsContentOfFileInMessageBody() { 
    Response response = handler.generateResponse(this.requestToExistingFile); 
    String stringifiedMessageBody = new String(response.getMessageBody());
    assertEquals(TEXT_FILE_CONTENT, stringifiedMessageBody);
  } 

  @Test 
  public void returnsOnlyHeadersForHeadRequest() {
    Request request = TestUtil.buildRequestToUri("HEAD", TEXT_FILE_URI);
    Response response = handler.generateResponse(request);
    String messageBody = new String(response.getMessageBody());
    assertEquals(200, response.getStatusCode()); 
    assertEquals("", messageBody); 
  }

}
