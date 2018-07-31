import java.io.IOException; 
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Test; 
 
public class FileHandlerTest { 
  private static Handler handler;
  private Request requestToExistingFile = buildRequestToExistingFile();
  private Request requestToNonexistentFile = buildRequestToNonexistentFile();
  
  @BeforeClass  
  public static void setUp() throws IOException, NonexistentDirectoryException { 
    String currentDirectoryPath = System.getProperty("user.dir"); 
    String testResourcesPath = currentDirectoryPath + "/src/test/resources/testFiles"; 

    Directory directory = new Directory(testResourcesPath); 
    handler = new FileHandler(directory); 
  } 
  
  @Test  
  public void returns404IfFileDoesNotExist() {  
    Response response = handler.generateResponse(this.requestToNonexistentFile); 
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
    assertEquals("This is a sample text file.\n", response.getMessageBody()); 
  } 

  private static Request buildRequestToNonexistentFile() { 
  return new Request.Builder() 
                    .method("GET") 
                    .uri("/nonexistent.txt") 
                    .version("1.1") 
                    .build(); 
  } 

  private static Request buildRequestToExistingFile() { 
  return new Request.Builder() 
                    .method("GET") 
                    .uri("/sample-text.txt") 
                    .version("1.1") 
                    .build(); 
  } 

}
