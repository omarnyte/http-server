import java.util.HashMap;
import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertTrue; 
import org.junit.BeforeClass; 
import org.junit.Test; 

public class FormHandlerTest {
  private final static String BODY = createBody();
  private final static String FIRST_KEY = "firstKey";
  private final static String FIRST_VAL = "firstVal";
  private final static String SECOND_KEY = "secondKey";
  private final static String SECOND_VAL = "secondVal";
  private final static String URI = "/api/form";
  
  private static Handler formHandler;
  private static String seeOtherUri;
  private static Response responseToPostRequest;
  private static Response responseToOptionsRequest;
  
  @BeforeClass
  public static void setUp() throws NonexistentDirectoryException {
    Directory mockDirectory = new MockDirectory();
    formHandler = new FormHandler(mockDirectory); 

    Request postRequest = TestUtil.buildRequestToUriWithBody(HttpMethod.POST, URI, BODY);
    responseToPostRequest = formHandler.generateResponse(postRequest);
    seeOtherUri = responseToPostRequest.getHeader(MessageHeader.LOCATION);

    Request optionsRequest = new Request.Builder().method(HttpMethod.OPTIONS).build();
    responseToOptionsRequest = formHandler.generateResponse(optionsRequest);
  }  
  
  @Test 
  public void returns200OkForOptionsRequest() {
    int expectedStatusCode = HttpStatusCode.OK;
    int actualStatusCode = responseToOptionsRequest.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = responseToOptionsRequest.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }
  
  @Test 
  public void returnsSupportedMethodsInAllowHeaderForOptionsRequest() {
    String expectedHeaderVal = "OPTIONS, POST";
    String actualHeaderVal = responseToOptionsRequest.getHeader(MessageHeader.ALLOW);
    assertEquals(expectedHeaderVal, actualHeaderVal);
  }
  
  @Test 
  public void returns400BadRequestWithMalformedBody() {
    String body = "keyWithoutValue=";
    Request request = TestUtil.buildRequestToUriWithBody(HttpMethod.POST, URI, body);
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
    int actualStatusCode = responseToPostRequest.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = responseToPostRequest.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  @Test
  public void returnsLocationHeaderFieldAfterSuccesfulPost() {
    HashMap<String, String> headers = responseToPostRequest.getHeaders();
    this.seeOtherUri = headers.get(MessageHeader.LOCATION);
    assertTrue(headers.containsKey(MessageHeader.LOCATION));
  }

  private static String createBody() {
    return String.format("%s=%s&%s=%s", FIRST_KEY, FIRST_VAL, SECOND_KEY, SECOND_VAL);
  }

}
