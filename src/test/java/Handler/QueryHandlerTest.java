import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class QueryHandlerTest {
  private final static String FIRST_KEY = "aKey"; 
  private final static String FIRST_VALUE = "aValue"; 
  private final static String SECOND_KEY = "anotherKey"; 
  private final static String SECOND_VALUE = "anotherValue"; 
  private final static String METHOD = "GET"; 
  private final static String URI = "api/query"; 

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

    String query = FIRST_KEY + "=" + FIRST_VALUE;
    Request request = buildRequest(METHOD, URI, query);
    
    String expectedString = FIRST_KEY + " : " + FIRST_VALUE + "\n";
    String actualString = new String(handler.generateResponse(request).getMessageBody());
    assertEquals(expectedString, actualString);
  }

  @Test 
  public void returnsMultipleQueryParametersWithNoReservedCharacters() {
    String query = String.format("%s=%s&%s=%s", FIRST_KEY, FIRST_VALUE, SECOND_KEY, SECOND_VALUE);
    Request request = buildRequest(METHOD, URI, query);
    
    String expectedString = String.format("%s : %s\n%s : %s\n", FIRST_KEY, FIRST_VALUE, SECOND_KEY, SECOND_VALUE);
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