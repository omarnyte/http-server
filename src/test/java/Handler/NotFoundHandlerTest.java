import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Test; 

public class NotFoundHandlerTest {
  private final static String NONEXISTENT_URI =  "/does-not-exist.txt";
  private final static String PLAIN_TEXT_BODY =  "plain text";

  private static Handler notFoundHandler;
  
  @BeforeClass 
  public static void setUp() throws NonexistentDirectoryException {
    Directory mockDirectory = new MockDirectory();
    notFoundHandler = new NotFoundHandler(mockDirectory);
  }

  @Test 
  public void returns404NotFoundForGetRequests() {
    Request request = TestUtil.buildRequestToUri("GET", NONEXISTENT_URI);
    Response response = notFoundHandler.generateResponse(request);

    int expectedStatusCode = HttpStatusCode.NOT_FOUND;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  @Test 
  public void returns404NotFoundForPatchRequests() {
    Request request = TestUtil.buildRequestToUri("PATCH", NONEXISTENT_URI);
    Response response = notFoundHandler.generateResponse(request);

    int expectedStatusCode = HttpStatusCode.NOT_FOUND;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  @Test 
  public void returns201CreatedForPutRequestToResourceThatDoesNotExist() {
    String body = "body";
    Request request = TestUtil.buildRequestToUriWithBody("PUT", NONEXISTENT_URI, body);
    Response response = notFoundHandler.generateResponse(request);

    int expectedStatusCode = HttpStatusCode.CREATED;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

}