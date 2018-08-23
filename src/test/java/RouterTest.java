import java.util.HashMap;
import static org.junit.Assert.assertEquals;
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

    MockDirectory mockDirectory = new MockDirectory();

    HashMap<String, Handler> routes = setUpRoutesWithMockCustomHandler(mockCustomHandler);

    router = new Router(mockDefaultHandler, routes, mockDirectory);
  }

  @Test 
  public void routesRequestToCustomHandler() {
    String method = "GET";
    Request request = TestUtil.buildRequestToUri(method, CUSTOM_URI);
    Response response = router.getResponse(request);
    String stringifiedMessageBody = new String(response.getMessageBody());
    assertEquals(MESSAGE_FROM_CUSTOM_HANDLER, stringifiedMessageBody);
  }

  @Test 
  public void routesRequestToDefaultHandlerWhenUriIsNotFound() {
    String method = "GET";
    String nonexistentUri = "some/nonexistent/uri";
    Request request = TestUtil.buildRequestToUri(method, nonexistentUri);
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

  private static HashMap<String, Handler> setUpRoutesWithMockCustomHandler(Handler mockCustomHandler) {
    HashMap<String, Handler> routes = new HashMap<String, Handler>();
    routes.put(CUSTOM_URI, mockCustomHandler);
    return routes;
  }

}
