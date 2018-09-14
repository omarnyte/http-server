import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CorsMiddlewareTest {
  private final static String HEADER_FIELD = "a-header-field";
  private final static String HEADER_VALUE = "a-header-value";
  private final static HashMap<String, String> ORIGINAL_HEADERS = createOriginalHeaders();
 
  @Test
  public void test() {
    CorsMiddleware corsMiddleware = new CorsMiddleware();
    Response originalResponse = new Response.Builder(HttpStatusCode.OK)
                                            .headers(ORIGINAL_HEADERS)
                                            .build();
    Response updatedResponse = corsMiddleware.applyMiddleware(originalResponse);
    
    HashMap<String, String> expectedHeaders = createExpectedHeaders();
    HashMap<String, String> actualHeaders = updatedResponse.getHeaders();
    assertEquals(expectedHeaders, actualHeaders);
  }

  private static HashMap<String, String> createOriginalHeaders() {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put(HEADER_FIELD, HEADER_VALUE);
    return headers;
  }

  private HashMap<String, String> createExpectedHeaders() {
    HashMap<String, String> headers = ORIGINAL_HEADERS;
    headers.put(MessageHeader.AC_ALLOW_ORIGIN, "*");     
    return headers;
  }
  
}
