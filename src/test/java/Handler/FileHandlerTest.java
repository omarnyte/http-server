import java.io.IOException; 
import java.util.Arrays; 
import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertTrue; 
import org.junit.BeforeClass; 
import org.junit.Test; 
 
public class FileHandlerTest { 
  private final static String NONEXISTENT_FILE_URI = "/does-not-exist.txt";
  private final static String TEXT_FILE_CONTENT = "This is a sample text file.";
  private final static String TEXT_FILE_URI = "/text-file.txt";
  
  private static Handler handler;
  private Request requestToExistingFile = buildRequestToExistingFile();
  private Request requestToNonexistentFile = buildRequestToNonexistentFile();

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
    byte[] expectedResponseInBytes = TEXT_FILE_CONTENT.getBytes();
    assertTrue(Arrays.equals(expectedResponseInBytes, response.getMessageBody())); 
  } 

  private static Request buildRequestToNonexistentFile() { 
  return new Request.Builder() 
                    .method("GET") 
                    .uri(NONEXISTENT_FILE_URI) 
                    .version("1.1") 
                    .build(); 
  } 

  private static Request buildRequestToExistingFile() { 
  return new Request.Builder() 
                    .method("GET") 
                    .uri(TEXT_FILE_URI) 
                    .version("1.1") 
                    .build(); 
  } 

}
