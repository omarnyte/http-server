import java.io.File; 
import java.io.IOException; 
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test; 
 
public class FileHandlerTest { 
  private static Handler handler; 
  private static Request requestToExistingFile = buildRequestToExistingFile(); 
  private static Request requestToNonexistentFile = buildRequestToNonexistentFile(); 

  @Rule
  public ExpectedException thrown = ExpectedException.none();  
   
  @BeforeClass  
  public static void setup() throws IOException, NonexistentDirectoryException { 
    String currentDirectoryPath = System.getProperty("user.dir"); 
    String testResourcesPath = currentDirectoryPath + "/src/test/resources/testFiles"; 
 
    handler = new FileHandler(testResourcesPath); 
  } 
   
  @Test 
  public void throwsNonexistentDirectoryExceptionWithInvalidPath() throws NonexistentDirectoryException {
    String nonexistentPath = "path/that/does/not/exist";

    thrown.expect(NonexistentDirectoryException.class);
    Handler fileHandler = new FileHandler(nonexistentPath);
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
 
  @Test public void returnsContentOfFileInMessageBody() { 
    Response response = handler.generateResponse(this.requestToExistingFile); 
    assertEquals("This is a sample text file.\n", response.getMessageBody()); 
  } 

  private static Request buildRequestToExistingFile() { 
    return new Request.Builder() 
                      .method("GET") 
                      .uri("/sample-text.txt") 
                      .version("1.1") 
                      .build(); 
  } 

  private static Request buildRequestToNonexistentFile() { 
    return new Request.Builder() 
                      .method("GET") 
                      .uri("/nonexistent.txt") 
                      .version("1.1") 
                      .build(); 
  } 
   
}
