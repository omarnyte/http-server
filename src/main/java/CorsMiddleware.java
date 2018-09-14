import java.util.HashMap;

public class CorsMiddleware extends Middleware {

  public Response applyMiddleware(Response originalResponse) {
    HashMap<String, String> updatedHeaders = originalResponse.getHeaders();
    updatedHeaders.put(MessageHeader.AC_ALLOW_ORIGIN, "*");
    Response updatedResponse = new Response.Builder(originalResponse.getStatusCode()) 
                       .headers(updatedHeaders)
                       .messageBody(originalResponse.getMessageBody())
                       .build();

    return checkNext(updatedResponse);
  }

}
