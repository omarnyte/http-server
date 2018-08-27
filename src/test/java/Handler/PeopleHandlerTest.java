import java.io.IOException; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Test; 

public class PeopleHandlerTest {
  private final static String PEOPLE_URI = "/people";
  private final static String SUPPORTED_MEDIA_TYPE = "application/json";
  private final static String UNSUPPORTED_MEDIA_TYPE = "unsupported/type";
  private final static HashMap<String, String> HEADERS_WITH_SUPPORTED_MEDIA = buildHeadersWithSupportedMediaType();
  
  private static Handler peopleHandler;

  @BeforeClass
  public static void setUp() throws NonexistentDirectoryException {
    Directory mockDirectory = new MockDirectory();
    peopleHandler = new PeopleHandler(mockDirectory);
  }

  @Test 
  public void returns415UnsupportedMediaTypeWithIncompatibleMediaType() {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put(MessageHeader.CONTENT_TYPE, "unsupported/type");
    Request request = new Request.Builder()
                                 .method("POST")
                                 .uri(PEOPLE_URI)
                                 .headers(headers)
                                 .build();
    Response response = peopleHandler.generateResponse(request);
                                 
    int expectedStatusCode = HttpStatusCode.UNSUPPORTED_MEDIA_TYPE;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  @Test 
  public void returns201CreatedWithCompatibleMediaType() {
    Request request = new Request.Builder()
                                 .method("POST")
                                 .uri(PEOPLE_URI)
                                 .headers(HEADERS_WITH_SUPPORTED_MEDIA)
                                 .build();
    Response response = peopleHandler.generateResponse(request);
                                 
    int expectedStatusCode = HttpStatusCode.CREATED;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  @Test 
  public void returns405MethodNotAllowedWithUnallowedMethod() {
    String method = "NOT_ALLOWED";
    Request request = new Request.Builder()
                                 .method(method)
                                 .uri(PEOPLE_URI)
                                 .headers(HEADERS_WITH_SUPPORTED_MEDIA)
                                 .build();
    Response response = peopleHandler.generateResponse(request);
                                 
    int expectedStatusCode = HttpStatusCode.METHOD_NOT_ALLOWED;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  private static HashMap<String, String> buildHeadersWithSupportedMediaType() {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put(MessageHeader.CONTENT_TYPE, SUPPORTED_MEDIA_TYPE);
    return headers;
  }

}
