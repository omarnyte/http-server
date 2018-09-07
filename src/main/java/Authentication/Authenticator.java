import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;

public class Authenticator {  
  private String authRoute;
  private Credentials credentials;
  private List<String> protectedUris;
  
  public Authenticator(Credentials credentials, List<String> protectedUris, String authRoute) {
    this.credentials = credentials;
    this.protectedUris = protectedUris;
    this.authRoute = authRoute;
  }

  public Request authenticateRequest(Request request) {
    try {
      return isProtected(request.getURI()) ? validateCredentials(request) : request;
    } catch (NullPointerException e) {
      return buildRequestToAuthRoute(request.getMethod());
    } 
  }

  private boolean isProtected(String uri) {
    return this.protectedUris.contains(uri);
  }

  private Request validateCredentials(Request request) {
    String authHeader = request.getHeader(MessageHeader.AUTHORIZATION);
    if (authHeader == null) {
      return buildRequestToAuthRoute(request.getMethod());
    } else {
      String[] splitDecodedCredentials = getSplitDecodedCredentials(authHeader);
      return identicalCredentials(splitDecodedCredentials) ? request : buildRequestToAuthRoute(request.getMethod());  
    }
  }

  private String[] getSplitDecodedCredentials(String authHeader) throws NullPointerException {
    String[] splitAuthHeader = authHeader.split(" ");
    String encodedCredentials = splitAuthHeader[1];
    Base64.Decoder decoder = Base64.getDecoder();
    String decodedCredentials = new String(decoder.decode(encodedCredentials));
    return decodedCredentials.split(":");
  }

  private boolean identicalCredentials(String[] splitDecodedCredentials) {
    String username = splitDecodedCredentials[0];
    String password = splitDecodedCredentials[1];
    return this.credentials.areValidCredentials(username, password);
  }

  private Request buildRequestToAuthRoute(String method) {
    return new Request.Builder()
                      .method(method)
                      .uri(this.authRoute)
                      .build();
  }
  
} 
