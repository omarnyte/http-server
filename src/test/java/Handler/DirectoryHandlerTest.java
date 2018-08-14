import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class DirectoryHandlerTest {
  private final static String HTML_FILE_URI = "/html-file.html";
  private final static String TEXT_FILE_URI = "/text-file.txt";
  
  private static Handler handler; 
  
  @BeforeClass
  public static void setup () throws IOException, NonexistentDirectoryException {
    String tempDirectoryPath = System.getProperty("user.dir") + "/src/test/java/Handler/DirectoryHandlerTestDirectory";

    TempDirectory temp = new TempDirectory(tempDirectoryPath);
    temp.createEmptyFile(TEXT_FILE_URI);
    temp.createEmptyFile(HTML_FILE_URI);

    Directory directory = new Directory(tempDirectoryPath); 
    handler = new DirectoryHandler(directory); 
  }
    
  @Test 
  public void returneContentsOfDirectoryAsHtml() {
    Request request = TestUtil.buildRequestToUri("GET", "/");

    String[] files = { TestUtil.removeLeadingParenthesesFromUri(HTML_FILE_URI),
                       TestUtil.removeLeadingParenthesesFromUri(TEXT_FILE_URI) 
                     };
    String expectedHtml = TestUtil.createRootHtmlFromFileNames(files);
    String messageBody = new String(handler.generateResponse(request).getMessageBody());
    assertEquals(expectedHtml, messageBody);
  }
  
  @Test 
  public void returnsOnlyHeadersForHeadRequest() {  
    Request request = TestUtil.buildRequestToUri("HEAD", "/");
    Response response = handler.generateResponse(request);
    String messageBody = new String(response.getMessageBody());
    assertEquals(HttpStatusCode.OK, response.getStatusCode()); 
    assertEquals("", messageBody); 
  }
  
  @Test
  public void return405MethodNotAllowed() {
    Request request = TestUtil.buildRequestToUri("POST", "/");
    Response response = handler.generateResponse(request);
    assertEquals(HttpStatusCode.METHOD_NOT_ALLOWED, response.getStatusCode()); 
    assertEquals("Method Not Allowed", response.getReasonPhrase()); 
  }

}
