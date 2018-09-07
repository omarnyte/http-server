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
    return isProtected(request.getURI()) ? validateCredentials(request) : request;
  }

  private boolean isProtected(String uri) {
    return this.protectedUris.contains(uri);
  }

  private Request validateCredentials(Request request) {
    String authHeader = request.getHeader(MessageHeader.AUTHORIZATION);
    if (authHeader == null) {
      return buildRequestToAuthRoute();
    }

    String[] splitDecodedCredentials = getSplitDecodedCredentials(authHeader);
    return identicalCredentials(splitDecodedCredentials) ? request : buildRequestToAuthRoute();  
  }

  private String[] getSplitDecodedCredentials(String authHeader) {
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

  private Request buildRequestToAuthRoute() {
    return new Request.Builder()
                      .uri(authRoute)
                      .build();
  }
  
} 
