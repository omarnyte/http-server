import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class RouterTest {
  private final static String CUSTOM_URI = "/custom/uri";
  private final static String MESSAGE_FROM_CUSTOM_HANDLER = "I come from the custom handler.";
  private final static String MESSAGE_FROM_DEFAULT_HANDLER = "I come from the default handler.";
  private static Router router;
  
  @BeforeClass 
  public static void setUp() throws NonexistentDirectoryException {
    Handler mockDefaultHandler = createMockHandlerThatRespondsWithMessage(MESSAGE_FROM_DEFAULT_HANDLER);
    Handler mockCustomHandler = createMockHandlerThatRespondsWithMessage(MESSAGE_FROM_CUSTOM_HANDLER);

    MockDirectory mockDirectory = setUpMockDirectory();

    HashMap<String, Handler> routes = setUpRoutesWithMockCustomHandler(mockCustomHandler);

    router = new Router(mockDefaultHandler, routes, mockDirectory);
  }

  @Test 
  public void routesRequestToCustomHandler() {
    Request request = buildRequestToURI(CUSTOM_URI);
    Response response = router.getResponse(request);
    String stringifiedMessageBody = new String(response.getMessageBody());
    assertEquals(MESSAGE_FROM_CUSTOM_HANDLER, stringifiedMessageBody);
  }

  @Test 
  public void routesRequestToDefaultHandlerWhenUriIsNotFound() {
    Request request = buildRequestToURI("some/other/uri");
    Response response = router.getResponse(request);
    String stringifiedMessageBody = new String(response.getMessageBody());
    assertEquals(MESSAGE_FROM_DEFAULT_HANDLER, stringifiedMessageBody);
  }

  private static Handler createMockHandlerThatRespondsWithMessage(String message) {
    Response response = new Response.Builder(HttpStatusCode.OK)
                                    .httpVersion("1.1")
                                    .messageBody(message)
                                    .build();

    return new MockHandler(response);
  }

  private static MockDirectory setUpMockDirectory() throws NonexistentDirectoryException {
    ArrayList<String> subdirectories = new ArrayList<String>();
    subdirectories.add("/some/subdirectory");
    ArrayList<String> files = new ArrayList<String>();
    files.add("some-directory.txt");
    return new MockDirectory(System.getProperty("user.dir") , subdirectories, files);
  }

  private static HashMap<String, Handler> setUpRoutesWithMockCustomHandler(Handler mockCustomHandler) {
    HashMap<String, Handler> routes = new HashMap<String, Handler>();
    routes.put(CUSTOM_URI, mockCustomHandler);
    return routes;
  }

  private static Request buildRequestToURI(String uri) {
    return new Request.Builder()
                      .method("GET")
                      .uri(uri)
                      .version("1.1")
                      .build();
  }

  private static class MockHandler implements Handler {
    Response response;
    
    public MockHandler(Response response) {
      this.response = response; 
    }

    public Response generateResponse(Request request) {
      return response;
    }
  }


  private static class MockDirectory extends Directory {
    List<String> subdirectories = new ArrayList<String>();
    List<String> files = new ArrayList<String>();    
    
    public MockDirectory(String directoryPath, List<String> subdirectories, List<String> files) throws NonexistentDirectoryException {
      super(directoryPath);
      this.subdirectories = subdirectories;
      this.files = files;
    }
    
    @Override
    public Boolean isDirectory(String uri) {
      return this.subdirectories.contains(uri);
    }

    @Override
    public Boolean isFile(String uri) {
      return this.files.contains(uri);
    }

    @Override
    public Directory createSubdirectory(String uri) throws NonexistentDirectoryException {
    public String[] listContent() {
      String[] stringArray = { "Not implemented" };
      return stringArray;
    }

    @Override
    public Boolean existsInStore(String uri) {
      return true;
    }

    @Override
    public byte[] readFile(String uri) {
      return "Not implemented".getBytes();
    }

    @Override
    public String getFileType(String uri) {
      return "Not implemented";
    }

    @Override
    public void postFile (String uri, byte[] content) {

    }

}
