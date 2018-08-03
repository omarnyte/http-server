import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class RootHandlerTest {
  private static Handler handler; 
  
  @BeforeClass
  public static void setup () throws IOException, NonexistentDirectoryException {
    String tempDirectoryPath = System.getProperty("user.dir") + "/src/test/java/Handler/RootHandlerTestDirectory";

    TempDirectory temp = new TempDirectory(tempDirectoryPath);
    temp.createEmptyFile("test-file.txt");
    temp.createEmptyFile("test-html.html");

    Directory directory = new Directory(tempDirectoryPath); 
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
    String expectedMessageBody = removeLeadingParentheses(HTML_FILE_URI) + "\n" + 
                                 removeLeadingParentheses(TEXT_FILE_URI) + "\n"; 
    byte[] expectedMessageBodyInBytes = expectedMessageBody.getBytes();
    assertTrue(Arrays.equals(expectedMessageBodyInBytes, response.getMessageBody()));
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
