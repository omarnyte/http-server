import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class RequestTest {
  private final static String BODY = "request body";
  private final static String FIRST_HEADER_FIELD = "First-Header-Field";
  private final static String FIRST_HEADER_VAL = "First-Header-Val";
  private final static String CONTENT_LENGTH = "100";
  private final static HashMap<String, String> HEADERS = setHeaders();
  private final static String METHOD = "GET";
  private final static String SECOND_HEADER_FIELD = "Second-Header-Field";
  private final static String SECOND_HEADER_VAL = "Second-Header-Val";
  private final static String URI = "/uri/path";
  private final static String VERSION = "1.1";
  
  private static Request request;
                  
  @BeforeClass 
  public static void setUp() {
    request = buildRequest();
  } 
                          
  @Test 
  public void getsMethod() {
    assertEquals(METHOD, request.getMethod());
  }

  @Test 
  public void getsURI() {
    assertEquals(URI, request.getURI());
  }

  @Test 
  public void getsHTTPVersion() {
    assertEquals(VERSION, request.getHTTPVersion());
  }

  @Test 
  public void getsHeadersHashMap() {
    HashMap<String, String> expectedHeaders = HEADERS;
    HashMap<String, String> actualHeaders = request.getHeaders();
    assertEquals(expectedHeaders, actualHeaders);
  }

  @Test 
  public void getsMessageBody() {
    String expectedBody = BODY;
    String actualBody = request.getBody();
    assertEquals(expectedBody, actualBody);
  }

  private static HashMap<String, String> setHeaders() {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put(FIRST_HEADER_FIELD, FIRST_HEADER_VAL);
    headers.put(SECOND_HEADER_FIELD, SECOND_HEADER_VAL);
    return headers;
  }

  private static Request buildRequest() {
    return new Request.Builder()
                      .method(METHOD)
                      .uri(URI)
                      .version(VERSION)
                      .headers(HEADERS)
                      .body(BODY)
                      .build();
  }

}
