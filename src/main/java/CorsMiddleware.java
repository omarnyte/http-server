import java.util.HashMap;

public class CorsMiddleware extends Middleware {

  public Response applyMiddleware(Response originalResponse) {
    HashMap<String, String> updatedHeaders = addCorsHeaders(originalResponse.getHeaders());
    Response updatedResponse = updateResponseWithHeaders(originalResponse, updatedHeaders);
    return checkNext(updatedResponse);
  }

  private HashMap<String, String> addCorsHeaders(HashMap<String, String> originalHeaders) {
    HashMap<String, String> updatedHeaders = originalHeaders;
    updatedHeaders.put(MessageHeader.AC_ALLOW_ORIGIN, "http://localhost:8888");
    updatedHeaders.put(MessageHeader.AC_ALLOW_CREDENTIALS, "true");
    updatedHeaders.put(MessageHeader.AC_ALLOW_METHODS, "DELETE");
    return updatedHeaders;
  }

  private Response updateResponseWithHeaders(Response oldResponse, HashMap<String, String> headers) {
    return new Response.Builder(oldResponse) 
                       .headers(headers)
                       .build();
  }

}
