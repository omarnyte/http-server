public class AuthHandler implements Handler {
  private final static String AUTH_SCHEME = "Basic";
  
  public Response generateResponse(Request request) {
    return new Response.Builder(HttpStatusCode.UNAUTHORIZED)
                       .setHeader(MessageHeader.AUTHENTICATE, AUTH_SCHEME)
                       .build();
  }

}
