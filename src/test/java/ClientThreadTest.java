import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClientThreadTest {
  private final static String EXPECTED_RESPONSE_BODY = "EXPECTED RESPONSE";

  private static MockSocket mockSocket;
  
  @BeforeClass
  public static void setUp() throws NonexistentDirectoryException {
    byte[] requestBytes = "GET /some/uri HTTP/1.1\r\n\r\n".getBytes();
    mockSocket = new MockSocket(requestBytes);
    Router mockRouter = setUpMockRouter();
    Middleware mockMiddleware = new MockMiddleware();

    ClientThread clientThread = new ClientThread(mockSocket, mockRouter, mockMiddleware);
    clientThread.run();
  }
  
  @Test 
  public void test() throws NonexistentDirectoryException {
    String expectedResponse = "HTTP/1.1 200 OK\r\n\r\n" + EXPECTED_RESPONSE_BODY;
    byte[] expectedResponseBytes = expectedResponse.getBytes();
    String actualResponse = mockSocket.getWrittenBytes().toString();
    assertEquals(expectedResponse, actualResponse);
  }

  private static Router setUpMockRouter() throws NonexistentDirectoryException {
    Response expectedResponse = new Response.Builder(HttpStatusCode.OK)
                                            .messageBody(EXPECTED_RESPONSE_BODY)
                                            .build();
    return MockRouter.createMockRouter(expectedResponse);
  }

}