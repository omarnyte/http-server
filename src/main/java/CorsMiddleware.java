import java.util.HashMap;

public class CorsMiddleware extends Middleware {

  public Response applyMiddleware(Response originalResponse) {
    HashMap<String, String> headers = originalResponse.getHeaders();
    headers.put(MessageHeader.AC_ALLOW_ORIGIN, "*");
    return new Response.Builder(originalResponse.getStatusCode()) 
                       .headers(originalResponse.getHeaders())
                       .messageBody(originalResponse.getMessageBody())
                       .build();
  }

}
