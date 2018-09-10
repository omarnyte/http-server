import java.util.HashMap;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class RequestLineParserTest {
  private final static String METHOD = "GET";
  private final static String URI = "/uri/path";
  private final static String QUERY= "aKey=aValue";
  private final static String URI_WITH_QUERY = URI + "?" + QUERY;
  private final static String VERSION = "HTTP/1.1";

  private static RequestLineParser requestLineWithoutQuery; 
  private static RequestLineParser requestLineWithQuery; 
  
  @BeforeClass
  public static void setUp() throws BadRequestException {
    requestLineWithoutQuery = createRequestLineParser(METHOD, URI, VERSION);
    requestLineWithQuery = createRequestLineParser(METHOD, URI_WITH_QUERY, VERSION);
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none(); 
  
  @Test 
  public void throwsBadRequestException() throws BadRequestException {
    String requestLineString = "GET\r\n";
    
    thrown.expect(BadRequestException.class);
    RequestLineParser requestLine = new RequestLineParser(requestLineString);
  }
  
  @Test 
  public void getsMethod() {
    assertEquals(METHOD, requestLineWithoutQuery.getMethod());
  }

  @Test 
  public void getsUriWithoutQuery() {
    assertEquals(URI, requestLineWithoutQuery.getURI());
  }

  @Test 
  public void getsUriWhenRequestContainsQuery() {
    assertEquals(URI, requestLineWithQuery.getURI());

  }

  @Test 
  public void getsEmptySTringIfNoQueryPresent() {
    assertEquals("", requestLineWithoutQuery.getQuery());
  }

  @Test 
  public void getsQueryIfPresent() {
    assertEquals(QUERY, requestLineWithQuery.getQuery());
  }

  @Test 
  public void getsUriWithSpaces() throws BadRequestException {
    String uri = "/file%20with%20spaces.txt";
    RequestLineParser requestLineParser = createRequestLineParser(METHOD, uri, VERSION);
    assertEquals("/file with spaces.txt", requestLineParser.getURI());
  }

  @Test 
  public void getsHTTPVersion() {
    assertEquals("1.1", requestLineWithoutQuery.getHTTPVersion());
  }

  private static RequestLineParser createRequestLineParser(String method, String uri, String version) throws BadRequestException {
    String requestLineString = String.format("%s %s %s", method, uri, version);
    return new RequestLineParser(requestLineString); 
  }

}