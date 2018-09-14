import java.io.IOException; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Test; 
 
public class FileHandlerTest { 
  private final static String JSON_PATCH_TYPE = "application/json/json-patch+json";
  private final static String IMAGE_FILE_URI = "/image.jpg";
  private final static String TEXT_FILE_CONTENT = "This is a sample text file.";
  private final static String TEXT_FILE_URI = "/text-file.txt";
  private final static String TO_BE_DELETED_URI = "/to-be-deleted.txt";
  private final static String TO_BE_MODIFIED_URI = "/to-be-modified.txt";
  
  private static Handler fileHandler;
  private static Response responseToGet; 
  private static Response responseToHead; 
  private static Response responseToOptions; 

  @BeforeClass
  public static void setUp() throws IOException, NonexistentDirectoryException {
    MockDirectory mockDirectory = setUpMockDirectory();
    fileHandler = new FileHandler(mockDirectory); 

    responseToGet = fileHandler.generateResponse(TestUtil.buildRequestToUri(HttpMethod.GET, TEXT_FILE_URI));
    responseToHead = fileHandler.generateResponse(TestUtil.buildRequestToUri(HttpMethod.HEAD, TEXT_FILE_URI));
    responseToOptions = fileHandler.generateResponse(TestUtil.buildRequestToUri(HttpMethod.OPTIONS, TEXT_FILE_URI));
  }

  @Test 
  public void returns200OkForOptionsRequest() {
    int expectedStatusCode = HttpStatusCode.OK;
    int actualStatusCode = responseToOptions.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = responseToOptions.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }
  
  @Test 
  public void returnsSupportedMethodsInAllowHeaderForOptionsRequestToNonPatchableResource() {
    responseToOptions = fileHandler.generateResponse(TestUtil.buildRequestToUri(HttpMethod.OPTIONS, IMAGE_FILE_URI));
    String expectedHeaderVal = "DELETE, GET, HEAD, OPTIONS";
    String actualHeaderVal = responseToOptions.getHeader(MessageHeader.ALLOW);
    assertEquals(expectedHeaderVal, actualHeaderVal);
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
    Request request = TestUtil.buildRequestToUri(HttpMethod.DELETE, TO_BE_DELETED_URI);
    Response response = fileHandler.generateResponse(request);

    int expectedStatusCode = HttpStatusCode.NO_CONTENT;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  @Test 
  public void returns200OkAfterSuccessfulPut() {
    Request request = TestUtil.buildRequestToUri(HttpMethod.PUT, TO_BE_MODIFIED_URI);
    Response response = fileHandler.generateResponse(request);

    int expectedStatusCode = HttpStatusCode.OK;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  private static MockDirectory setUpMockDirectory() throws NonexistentDirectoryException {
    List<String> subdirectories = setUpMockSubdirectories();
    List<String> files = setUpMockFiles();   
    HashMap<String, String> fileContents = setUpMockFileContents();
    Map<String, String> fileTypes = setUpMockFileTypes();
    return new MockDirectory(subdirectories, files, fileContents, fileTypes);
  }

  private static List<String> setUpMockSubdirectories() {
    ArrayList<String> subdirectories = new ArrayList<String>();
    return subdirectories;
  }

  private static List<String> setUpMockFiles() {
    ArrayList<String> files = new ArrayList<String>();
    files.add(IMAGE_FILE_URI);
    files.add(TO_BE_DELETED_URI);
    files.add(TO_BE_DELETED_URI);
    return files;
  }

  private static HashMap<String, String> setUpMockFileContents() {
    HashMap<String, String> fileContents = new HashMap<String, String>();
    fileContents.put(TEXT_FILE_URI, TEXT_FILE_CONTENT);
    return fileContents;
  }

  private static Map<String, String> setUpMockFileTypes() {
    return Map.ofEntries(
      Map.entry(TEXT_FILE_URI, MimeType.PLAIN_TEXT),
      Map.entry(IMAGE_FILE_URI, "unpatchable/file-type")
    );
  }

}
