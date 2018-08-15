import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Test; 
 
public class FileHandlerTest { 
  private final static String NONEXISTENT_FILE_URI = "/does-not-exist.txt";
  private final static String TEXT_FILE_CONTENT = "This is a sample text file.";
  private final static String TEXT_FILE_URI = "/text-file.txt";
  private final static String TO_BE_DELETED_URI = "/to-be-deleted.txt";
  
  private static Handler handler;
  private Request requestToExistingFile = TestUtil.buildRequestToUri("GET", TEXT_FILE_URI);

  @BeforeClass
  public static void setUp() throws IOException, NonexistentDirectoryException {
    DataStore mockDirectory = setUpMockDirectory();
    handler = new FileHandler(mockDirectory); 
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

  @Test 
  public void returns204ForDeleteRequest() {
    Request request = TestUtil.buildRequestToUri("DELETE", TO_BE_DELETED_URI);
    Response response = handler.generateResponse(request);
    String messageBody = new String(response.getMessageBody());
    assertEquals(204, response.getStatusCode()); 
  }

  private static MockDirectory setUpMockDirectory() throws NonexistentDirectoryException {
    List<String> subdirectories = setUpMockSubdirectories();
    List<String> files = setUpMockFiles();   
    Map<String, String> fileContents = setUpMockFileContents();
    Map<String, String> fileTypes = setUpMockFileContents();
    return new MockDirectory(subdirectories, files, fileContents, fileTypes);
  }

  private static List<String> setUpMockSubdirectories() {
    ArrayList<String> subdirectories = new ArrayList<String>();
    return subdirectories;
  }

  private static List<String> setUpMockFiles() {
    ArrayList<String> files = new ArrayList<String>();
    files.add(TEXT_FILE_URI);
    files.add(TO_BE_DELETED_URI);
    return files;
  }

  private static Map<String, String> setUpMockFileContents() {
    return Map.ofEntries(
      Map.entry(TEXT_FILE_URI, TEXT_FILE_CONTENT)
    );
  }

  private static Map<String, String> setUpMockFileTypes() {
    return Map.ofEntries(
      Map.entry(TEXT_FILE_URI, TEXT_FILE_CONTENT)
    );
  }

}
