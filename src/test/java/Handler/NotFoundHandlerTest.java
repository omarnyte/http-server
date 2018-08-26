import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Test; 

public class NotFoundHandlerTest {
  private final static String NONEXISTENT_URI =  "/does-not-exist.txt";

  private static Handler notFoundHandler;
  
  @BeforeClass 
  public static void setUp() throws NonexistentDirectoryException {
    notFoundHandler = new NotFoundHandler();
  }

  @Test 
  public void returns404NotFound() {
    Request request = TestUtil.buildRequestToUri("GET", NONEXISTENT_URI);
    Response response = notFoundHandler.generateResponse(request);

    int expectedStatusCode = HttpStatusCode.NOT_FOUND;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

}