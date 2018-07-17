import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RootHandlerTest {
  private String testDirectoryPath;
  
  @Before
  public void setup () throws IOException {
    String currentDirectoryPath = System.getProperty("user.dir");
    this.testDirectoryPath = currentDirectoryPath + "/src/test/java/Handler/RootHandlerTestDirectory";
    File testDirectory = new File(this.testDirectoryPath);
    testDirectory.mkdir();
    testDirectory.deleteOnExit();
    
    String testTxtFilePath = this.testDirectoryPath + "/test-file.txt";
    File testTxtFile = new File(testTxtFilePath);
    testTxtFile.createNewFile(); 
    testTxtFile.deleteOnExit();

    String testHTMLFilePath = this.testDirectoryPath + "/test-html.html";
    File testHTMLFile = new File(testHTMLFilePath);
    testHTMLFile.createNewFile(); 
    testHTMLFile.deleteOnExit();

  }
    

  @Test
  public void returnContentsOfDirectory() {
    Request request = new Request.Builder()
                                  .method("GET")
                                  .uri("/")
                                  .version("1.1")
                                  .build();                           
  
    RootHandler handler = new RootHandler(this.testDirectoryPath);
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

    RootHandler handler = new RootHandler(this.testDirectoryPath);
    Response response = handler.generateResponse(request);

    assertEquals(405, response.getStatusCode()); 
    assertEquals("Method Not Allowed", response.getReasonPhrase()); 
  }
  
}
