import java.io.File; 
import java.io.IOException; 
import java.util.HashMap;
import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertTrue; 
import org.junit.AfterClass; 
import org.junit.BeforeClass; 
import org.junit.Test; 

public class FormHandlerTest {
  private final static String FIRST_KEY = "firstKey";
  private final static String FIRST_VAL = "firstVal";
  private final static String SECOND_KEY = "secondKey";
  private final static String SECOND_VAL = "secondVal";
  private final static String TEMP_ROOT_DIRECTORY_PATH = System.getProperty("user.dir") + "/src/test/java/Handler/FormHandlerTestDirectory";
  
  private static String seeOtherUri;
  private static Handler handler;
  private static Response response;
  private static Directory directory;
  
  @BeforeClass
  public static void setUp() throws IOException, NonexistentDirectoryException {
    String tempPostedDirPath = TEMP_ROOT_DIRECTORY_PATH + "/POSTed";

    TempDirectory tempRootDirectory = new TempDirectory(TEMP_ROOT_DIRECTORY_PATH);
    TempDirectory tempPosted = new TempDirectory(tempPostedDirPath);

    directory = new Directory(TEMP_ROOT_DIRECTORY_PATH); 
    handler = new FormHandler(directory); 
    
    Request request = new Request.Builder()
                                 .method("POST")
                                 .uri("/api/form")
                                 .version("1.1")
                                 .addMessageBodyKeyVal(FIRST_KEY, FIRST_VAL)
                                 .addMessageBodyKeyVal(SECOND_KEY, SECOND_VAL)
                                 .build();
    response = handler.generateResponse(request);
    
    seeOtherUri = response.getHeader(MessageHeader.LOCATION);
  }
  
  @Test
  public void returnsStatusCode303SeeOther() {
    int statusCode = response.getStatusCode();
    String reasonPhrase = response.getReasonPhrase();
    assertEquals(HttpStatusCode.SEE_OTHER, statusCode);
    assertEquals(HttpStatusCode.getReasonPhrase(HttpStatusCode.SEE_OTHER), response.getReasonPhrase());
  }

  @Test
  public void returnsLocationHeaderField() {
    HashMap<String, String> headers = response.getHeaders();
    this.seeOtherUri = headers.get(MessageHeader.LOCATION);
    assertTrue(headers.containsKey(MessageHeader.LOCATION));
  }

  @Test
  public void createsResourceBasedOnPostedData() {
    assertTrue(directory.existsInStore(seeOtherUri));
  }

  @AfterClass 
  public static void tearDown() {
    File createdFile = new File(TEMP_ROOT_DIRECTORY_PATH + seeOtherUri);
    createdFile.delete();
  }

}
