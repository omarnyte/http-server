import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class RootHandlerTest {
  private Request goodRequest;
  private String jarDirectoryPath;
  
    @Before 
    public void setup() {
      
      String rootPath = System.getProperty("user.dir");
      this.jarDirectoryPath = rootPath + "/build/libs";
      
      this.goodRequest = new Request.Builder()
                                    .method("GET")
                                    .uri("/")
                                    .version("1.1")
                                    .build();
    }
  
    @Test
    public void returnContentsOfDirectory() {
      RootHandler handler = new RootHandler(this.jarDirectoryPath);
      Response response = handler.generateResponse(this.goodRequest);

      assertEquals("hello-root.html\n" + 
                   "hello-root.txt\n" +
                   "http-server.jar\n", response.getMessageBody());
    }
  
}
