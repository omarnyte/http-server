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
  public static void setUpRouter() {
    Handler mockDefaultHandler = createMockHandlerThatRespondsWithMessage(MESSAGE_FROM_DEFAULT_HANDLER);
    Handler mockCustomHandler = createMockHandlerThatRespondsWithMessage(MESSAGE_FROM_CUSTOM_HANDLER);

    HashMap<String, Handler> routes = new HashMap<String, Handler>();
    routes.put(CUSTOM_URI, mockCustomHandler);
    router = new Router(mockDefaultHandler, routes);
  }

  @Test 
  public void routesRequestToCorrectHandler() {
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

}
