import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Test; 

public class AuthenticatorTest {
  private static final String AUTH_ROUTE = "/auth/route";
  private static final String INVALID_ENCODED_CREDENTIALS= "aW52YWxpZHVzZXJuYW1lOmludmFsaWRwYXNzd29yZA0K";
  private static final String PROTECTED_URI = "/protected/uri";
  private static final List<String> PROTECTED_URIS = Arrays.asList(
    PROTECTED_URI
    );  
    private static final String UNPROTECTED_URI = "/unprotected/uri";
    private static final String VALID_ENCODED_CREDENTIALS= "dXNlcm5hbWU6cGFzc3dvcmQ=";

  private static Authenticator authenticator;
  
  @BeforeClass
  public static void setUp() {
    Credentials credentials = new Credentials("username", "password");
    authenticator = new Authenticator(credentials, PROTECTED_URIS, AUTH_ROUTE);
  }
  
  @Test 
  public void doesNotAlterRequestIfUriIsUnprotected() {
    Request request = new Request.Builder()
                                 .method(HttpMethod.GET)
                                 .uri(UNPROTECTED_URI)
                                 .build();
    request = authenticator.authenticateRequest(request);
    assertEquals(UNPROTECTED_URI, request.getURI());
  }

  @Test 
  public void altersUnauthenticatedRequestIfUriIsProtected() {
    Request request = new Request.Builder()
                                 .method(HttpMethod.GET)
                                 .uri(PROTECTED_URI)
                                 .build();
    request = authenticator.authenticateRequest(request);
    assertEquals(AUTH_ROUTE, request.getURI());
  }

  @Test 
  public void altersRequestWithInvalidCredentialstIfUriIsProtected() {
    String authHeaderValue = "Basic " + INVALID_ENCODED_CREDENTIALS;
    Request request = new Request.Builder()
                        .method(HttpMethod.GET)
                        .setHeader(MessageHeader.AUTHORIZATION, authHeaderValue)
                        .uri(PROTECTED_URI)
                        .build();
    request = authenticator.authenticateRequest(request);
    assertEquals(AUTH_ROUTE, request.getURI());
  }
  @Test 

  public void doesNotAlterRequestWithValidCredentialstIfUriIsProtected() {
    String authHeaderValue = "Basic " + VALID_ENCODED_CREDENTIALS;
    Request request = new Request.Builder()
                        .method(HttpMethod.GET)
                        .setHeader(MessageHeader.AUTHORIZATION, authHeaderValue)
                        .uri(PROTECTED_URI)
                        .build();
    request = authenticator.authenticateRequest(request);
    assertEquals(PROTECTED_URI, request.getURI());
  }

}