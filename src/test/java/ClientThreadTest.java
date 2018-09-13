import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClientThreadTest {
  private final static String EXPECTED_RESPONSE_BODY = "EXPECTED RESPONSE";
  
  private Router mockRouter;
  private MockSocket mockSocket;
  
  @Before
  public void setUp() throws NonexistentDirectoryException {
    byte[] requestBytes = "GET /some/uri HTTP/1.1\r\n\r\n".getBytes();
    mockSocket = new MockSocket(requestBytes);
    mockRouter = setUpMockRouter();
  }
  
  @Test 
  public void writesResponseUnalateredByMiddleware() throws NonexistentDirectoryException {
    Middleware mockMiddleware = createMockMiddlewareThatReturnsResponseBody(EXPECTED_RESPONSE_BODY);
    ClientThread clientThread = new ClientThread(this.mockSocket, this.mockRouter, mockMiddleware);
    clientThread.run();
    
    String expectedResponse = "HTTP/1.1 200 OK\r\n\r\n" + EXPECTED_RESPONSE_BODY;
    byte[] expectedResponseBytes = expectedResponse.getBytes();
    String actualResponse = mockSocket.getWrittenBytes().toString();
    assertEquals(expectedResponse, actualResponse);
  }

  @Test 
  public void writesResponseAlteredBySingleMiddleware() throws NonexistentDirectoryException {
    String bodyAlteredByMiddlware = "RESPONSE ALTERED BY MIDDLEWARE";
    Middleware mockMiddleware = createMockMiddlewareThatReturnsResponseBody(bodyAlteredByMiddlware);
    ClientThread clientThread = new ClientThread(this.mockSocket, this.mockRouter, mockMiddleware);
    clientThread.run();
    
    String expectedResponse = "HTTP/1.1 200 OK\r\n\r\n" + bodyAlteredByMiddlware;
    byte[] expectedResponseBytes = expectedResponse.getBytes();
    String actualResponse = mockSocket.getWrittenBytes().toString();
    assertEquals(expectedResponse, actualResponse);
  }

  @Test 
  public void writesResponseAlteredByMultiMiddlewareChain() throws NonexistentDirectoryException {
    String expectedBody = "RESPONSE ALTERED BY LAST MIDDLEWARE";
    Middleware mockMiddleware = createMultiMockMiddlewareChain(expectedBody);
    ClientThread clientThread = new ClientThread(this.mockSocket, this.mockRouter, mockMiddleware);
    clientThread.run();
    
    String expectedResponse = "HTTP/1.1 200 OK\r\n\r\n" + expectedBody;
    byte[] expectedResponseBytes = expectedResponse.getBytes();
    String actualResponse = mockSocket.getWrittenBytes().toString();
    assertEquals(expectedResponse, actualResponse);
  }

  private Router setUpMockRouter() throws NonexistentDirectoryException {
    Response expectedResponse = new Response.Builder(HttpStatusCode.OK)
                                            .messageBody(EXPECTED_RESPONSE_BODY)
                                            .build();
    return MockRouter.createMockRouter(expectedResponse);
  }

  private Middleware createMockMiddlewareThatReturnsResponseBody(String expectedResponseBody) {
    Response expectedResponse = new Response.Builder(HttpStatusCode.OK)
                                            .messageBody(expectedResponseBody)
                                            .build();
    return new MockMiddleware(expectedResponse);
  }

  private Middleware createMultiMockMiddlewareChain(String bodyReturnedByLastMiddleware) {
    Middleware firstMiddleware = createMockMiddlewareThatReturnsResponseBody("RESPONSE ALTERED BY FIRST MIDDLEWARE");
    Middleware secondMiddleware = createMockMiddlewareThatReturnsResponseBody("RESPONSE ALTERED BY SECOND MIDDLEWARE");
    Middleware thirdMiddleware = createMockMiddlewareThatReturnsResponseBody(bodyReturnedByLastMiddleware);

    return firstMiddleware.linkWith(secondMiddleware)
                          .linkWith(thirdMiddleware);
  }

}
