import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class RootHandlerTest {
  private final static String HTML_FILE_URI = "/html-file.html";
  private final static String TEXT_FILE_URI = "/text-file.txt";
  private final static int PORT = 8888;
  
  private static Handler handler; 
  
  @BeforeClass
  public static void setup () throws IOException, NonexistentDirectoryException {
    String tempDirectoryPath = System.getProperty("user.dir") + "/src/test/java/Handler/RootHandlerTestDirectory";

    TempDirectory temp = new TempDirectory(tempDirectoryPath);
    temp.createEmptyFile(TEXT_FILE_URI);
    temp.createEmptyFile(HTML_FILE_URI);

    Directory directory = new Directory(tempDirectoryPath); 
    handler = new RootHandler(directory, PORT); 
  }
    
  @Test 
  public void returneContentsOfDirectoryAsHtml() {
    Request request = new Request.Builder()
                                 .method("GET")
                                 .uri("/")
                                 .version("1.1")
                                 .build();   

    String[] uris = { HTML_FILE_URI, TEXT_FILE_URI };
    String expectedHtml = createExpectedHtmlFromFileUris(uris);
    String messageBody = new String(handler.generateResponse(request).getMessageBody());
    assertEquals(expectedHtml, messageBody);
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

  private String createExpectedHtmlFromFileUris(String[] uris) {
    String expectedHtml = "";
    for (String uri : uris) {
      String fileName = TestUtil.removeLeadingParenthesesFromUri(uri);
      expectedHtml += String.format(
        "<a href=\"http://localhost:%d/%s\">%s</a>" + 
        "<br>", PORT, fileName, fileName);
    }

    return expectedHtml;
  }
  
}
