import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Test; 
 
public class FileHandlerTest { 
  private final static String TEXT_FILE_CONTENT = "This is a sample text file.";
  private final static String TEXT_FILE_URI = "/text-file.txt";
  private final static String TO_BE_DELETED_URI = "/to-be-deleted.txt";
  
  private static Handler fileHandler;
  private static Response responseToGet; 
  private static Response responseToHead; 

  @BeforeClass
  public static void setUp() throws IOException, NonexistentDirectoryException {
    MockDirectory mockDirectory = setUpMockDirectory();
    fileHandler = new FileHandler(mockDirectory); 

    responseToGet = fileHandler.generateResponse(TestUtil.buildRequestToUri("GET", TEXT_FILE_URI));
    responseToHead = fileHandler.generateResponse(TestUtil.buildRequestToUri("HEAD", TEXT_FILE_URI));
  }

  @Test  
  public void returns200OkForGetRequest() { 
    int expectedStatusCode = HttpStatusCode.OK;
    int actualStatusCode = responseToGet.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = responseToGet.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  } 

  @Test 
  public void returnsContentOfFileInMessageBody() { 
    String stringifiedMessageBody = new String(responseToGet.getMessageBody());
    assertEquals(TEXT_FILE_CONTENT, stringifiedMessageBody);
  } 

  @Test  
  public void returns200OkForHeadRequest() { 
    int expectedStatusCode = HttpStatusCode.OK;
    int actualStatusCode = responseToHead.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = responseToHead.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  } 
  
  @Test 
  public void doesNotReturnMessageBodyForHeadRequest() {
    String messageBody = new String(responseToHead.getMessageBody());
    assertEquals("", messageBody); 
  }

  @Test 
  public void returns204NoContentForDeleteRequest() {
    Request request = TestUtil.buildRequestToUri("DELETE", TO_BE_DELETED_URI);
    Response response = fileHandler.generateResponse(request);

    int expectedStatusCode = HttpStatusCode.NO_CONTENT;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
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
