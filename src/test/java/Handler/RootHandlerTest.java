import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class RootHandlerTest {
  private static Handler handler; 
  
  @BeforeClass
  public static void setup () throws IOException, NonexistentDirectoryException {
    String currentDirectoryPath = System.getProperty("user.dir");
    String testDirectoryPath = currentDirectoryPath + "/src/test/java/Handler/RootHandlerTestDirectory";
    File testDirectory = new File(testDirectoryPath);
    testDirectory.mkdir();
    testDirectory.deleteOnExit();
    
    String testTxtFilePath = testDirectoryPath + "/test-file.txt";
    File testTxtFile = new File(testTxtFilePath);
    testTxtFile.createNewFile(); 
    testTxtFile.deleteOnExit();

    String testHTMLFilePath = testDirectoryPath + "/test-html.html";
    File testHTMLFile = new File(testHTMLFilePath);
    testHTMLFile.createNewFile(); 
    testHTMLFile.deleteOnExit();

    Directory directory = new Directory(testDirectoryPath); 
    handler = new RootHandler(directory); 
  }
    
  @Test
  public void returnContentsOfDirectory() {
    Request request = new Request.Builder()
                                 .method("GET")
                                 .uri("/")
                                 .version("1.1")
                                 .build();                           
  
    Response response = handler.generateResponse(request);

  assertEquals("test-file.txt\n" + 
               "test-html.html\n", response.getMessageBody());
  }  
  
  @Test
  public void return405MethodNotAllowed() {
    Request request = new Request.Builder()
                                 .method("POST")
                                 .uri("/")
                                 .version("1.1")
                                 .build();                           

    Response response = handler.generateResponse(request);

    assertEquals(405, response.getStatusCode()); 
    assertEquals("Method Not Allowed", response.getReasonPhrase()); 
  }
  
}
