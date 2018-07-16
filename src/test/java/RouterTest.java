import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class RouterTest {
  private HashMap<String, Handler> routes; 
  private Router router;
  
  @Before 
  public void setup() {
    this.routes = new HashMap<String, Handler>();
    String rootPath = System.getProperty("user.dir");
    String jarDirectoryPath = rootPath + "/build/libs";
    this.routes.put("/", new RootHandler(jarDirectoryPath));

    this.router = new Router(routes);
  }
  
  @Test 
  public void returnsNotFoundHandlerWhenURIIsNotFound() {
    Request request = new Request.Builder()
                                 .method("GET")
                                 .uri("/path/that/does/not/exist")
                                 .version("1.1")
                                 .build();                      
                                 
    String expectedResponseString = "HTTP/1.1 404 Not Found\r\n" + 
                                    "\r\n";
    Response response = this.router.getResponse(request);
    String responseString = response.toString();
    assertEquals(expectedResponseString, responseString);
  }
  
  @Test 
  public void returnsRootHandler() {
    Request request = new Request.Builder()
                                 .method("GET")
                                 .uri("/")
                                 .version("1.1")
                                 .build();

    
    String expectedResponseString = "HTTP/1.1 200 OK\r\n" + 
                                    "\r\n" + 
                                    "http-server.jar\n";
    Response response = this.router.getResponse(request);
    String responseString = response.toString();
    assertEquals(expectedResponseString, responseString);
  }

}
