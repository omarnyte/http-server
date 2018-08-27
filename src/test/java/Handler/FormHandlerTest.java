import java.util.HashMap;
import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertTrue; 
import org.junit.BeforeClass; 
import org.junit.Test; 

public class FormHandlerTest {
  private final static String BODY = createBody();
  private final static String FIRST_KEY = "firstKey";
  private final static String FIRST_VAL = "firstVal";
  private final static String POST_METHOD = "POST";
  private final static String SECOND_KEY = "secondKey";
  private final static String SECOND_VAL = "secondVal";
  private final static String URI = "/api/form";
  
  private static Handler formHandler;
  private static String seeOtherUri;
  private static Response response;
  
  @BeforeClass
  public static void setUp() throws NonexistentDirectoryException {
    Directory mockDirectory = new MockDirectory();
    formHandler = new FormHandler(mockDirectory); 

    Request request = TestUtil.buildRequestToUriWithBody(POST_METHOD, URI, BODY);
    response = formHandler.generateResponse(request);
    seeOtherUri = response.getHeader(MessageHeader.LOCATION);
  }  
  
  @Test 
  public void returns400BadRequestWithMalformedBody() {
    String body = "keyWithoutValue=";
    Request request = TestUtil.buildRequestToUriWithBody("POST", URI, body);
    Response response = formHandler.generateResponse(request);
                                 
    int expectedStatusCode = HttpStatusCode.BAD_REQUEST;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }
  
  @Test
  public void returns303SeeOtherAfterSuccessfulPost() {
    int expectedStatusCode = HttpStatusCode.SEE_OTHER;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  @Test
  public void returnsLocationHeaderFieldAfterSuccesfulPost() {
    HashMap<String, String> headers = response.getHeaders();
    this.seeOtherUri = headers.get(MessageHeader.LOCATION);
    assertTrue(headers.containsKey(MessageHeader.LOCATION));
  }

  private static String createBody() {
    return String.format("%s=%s&%s=%s", FIRST_KEY, FIRST_VAL, SECOND_KEY, SECOND_VAL);
  }

}
