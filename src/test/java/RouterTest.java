import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class RouterTest {
  private static String customURI = "/custom/uri";
  private static String messageFromCustomHandler = "I come from the custom handler.";
  private static String messageFromDefaultHandler = "I come from the default handler.";
  private static Router router;
  
  @BeforeClass 
  public static void setUpRouter() {
    Handler mockDefaultHandler = createMockHandlerThatRespondsWithMessage(messageFromDefaultHandler);
    Handler mockCustomHandler = createMockHandlerThatRespondsWithMessage(messageFromCustomHandler);

    HashMap<String, Handler> routes = new HashMap<String, Handler>();
    routes.put(customURI, mockCustomHandler);
    router = new Router(mockDefaultHandler, routes);
  }

  @Test 
  public void routesRequestToCorrectHandler() {
    Request request = buildRequestToURI(customURI);
    Response response = router.getResponse(request);
    assertEquals(messageFromCustomHandler, response.getMessageBody());
  }

  @Test 
  public void routesRequestToDefaultHandlerWhenUriIsNotFound() {
    Request request = buildRequestToURI("some/other/uri");
    Response response = router.getResponse(request);
    assertEquals(messageFromDefaultHandler, response.getMessageBody());
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
