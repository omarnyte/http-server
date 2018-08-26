import java.util.HashMap;
import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertTrue; 
import org.junit.BeforeClass; 
import org.junit.Test; 

public class FormHandlerTest {
  private final static String FIRST_KEY = "firstKey";
  private final static String FIRST_VAL = "firstVal";
  private final static String POST_METHOD = "POST";
  private final static String SECOND_KEY = "secondKey";
  private final static String SECOND_VAL = "secondVal";
  private final static String URI = "/api/form";
  
  private static String seeOtherUri;
  private static Response response;
  
  @BeforeClass
  public static void setUp() throws NonexistentDirectoryException {
    Directory mockDirectory = new MockDirectory();
    Handler formHandler = new FormHandler(mockDirectory); 

    Request request = TestUtil.buildRequestToUriWithMessageBody(POST_METHOD, URI, createMessageBody());
    response = formHandler.generateResponse(request);
    seeOtherUri = response.getHeader(MessageHeader.LOCATION);
  }
  
  @Test
  public void returnsStatusCode303SeeOther() {
    int statusCode = response.getStatusCode();
    String reasonPhrase = response.getReasonPhrase();
    assertEquals(HttpStatusCode.SEE_OTHER, statusCode);
    assertEquals(HttpStatusCode.getReasonPhrase(HttpStatusCode.SEE_OTHER), response.getReasonPhrase());
  }

  @Test
  public void returnsLocationHeaderField() {
    HashMap<String, String> headers = response.getHeaders();
    this.seeOtherUri = headers.get(MessageHeader.LOCATION);
    assertTrue(headers.containsKey(MessageHeader.LOCATION));
  }

  private static HashMap<String, String> createMessageBody() {
    HashMap<String, String> messageBody = new HashMap<String, String>();
    messageBody.put(FIRST_KEY, FIRST_VAL);
    messageBody.put(SECOND_KEY, SECOND_VAL);
    return messageBody;
  }

}
