import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class QueryHandlerTest {
  private final static String KEY = "aKey"; 
  private final static String METHOD = "GET"; 
  private final static String URI = "api/query"; 
  private final static String VALUE = "aValue"; 

  private static QueryHandler handler = new QueryHandler();
  
  @Test 
  public void returns400BadRequestWithMalformedQuery() {
    String query = "aKeyWithoutAValue";
    Request request = buildRequest(METHOD, URI, query);
    Response response = handler.generateResponse(request);
    
    int expectedStatusCode = HttpStatusCode.BAD_REQUEST;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }
  
  @Test 
  public void returnsSingleQueryParametersWithNoReservedCharacters() {
    String query = KEY + "=" + VALUE;
    Request request = buildRequest(METHOD, URI, query);
    
    String expectedString = KEY + " : " + VALUE;
    String actualString = new String(handler.generateResponse(request).getMessageBody());
    assertEquals(expectedString, actualString);
  }

  private Request buildRequest(String method, String uri, String query) {
    return new Request.Builder()
                      .method(method)
                      .uri(uri)
                      .query(query)
                      .build();
  }
  
}