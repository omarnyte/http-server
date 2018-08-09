import java.io.File; 
import java.io.IOException; 
import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertTrue; 
import org.junit.AfterClass; 
import org.junit.BeforeClass; 
import org.junit.Test; 

public class FormHandlerTest {
  private final static String HUMAN_NAME = "Martin";
  private final static String PET_TYPE = "cats";
  private final static String TEMP_ROOT_DIRECTORY_PATH = System.getProperty("user.dir") + "/src/test/java/Handler/FormHandlerTestDirectory";
  
  private static String seeOtherUri;
  private static Handler handler;
  
  @BeforeClass
  public static void setUp() throws IOException, NonexistentDirectoryException {
    String tempPostedDirPath = TEMP_ROOT_DIRECTORY_PATH + "/POSTed";

    TempDirectory tempRootDirectory = new TempDirectory(TEMP_ROOT_DIRECTORY_PATH);
    TempDirectory tempPosted = new TempDirectory(tempPostedDirPath);

    store = new Directory(TEMP_ROOT_DIRECTORY_PATH); 
    handler = new FormHandler(store); 
  }
  
  @Test
  public void createsResourceBasedOnSubmittedData() {
    Request request = new Request.Builder()
                                 .method("POST")
                                 .uri("/api/form")
                                 .version("1.1")
                                 .addMessageBodyKeyVal("humanName", "Martin")
                                 .addMessageBodyKeyVal("type", "cat")
                                 .build();

    Response response = handler.generateResponse(request);
    assertEquals(HttpStatusCode.SEE_OTHER, response.getStatusCode());
  }

  @Test
  public void returnsLocationHeaderField() {
    HashMap<String, String> headers = response.getHeaders();
    this.seeOtherUri = headers.get(MessageHeader.LOCATION);
    assertTrue(headers.containsKey(MessageHeader.LOCATION));
  }

  @Test
  public void createsResourceBasedOnPostedData() {
    System.out.println(seeOtherUri);
    assertTrue(store.existsInStore(seeOtherUri));
  }

  @AfterClass 
  public static void tearDown() {
    File createdFile = new File(TEMP_ROOT_DIRECTORY_PATH + seeOtherUri);
    System.out.println("DELETED CREATED FILE? : " + createdFile.delete());
  }

}
