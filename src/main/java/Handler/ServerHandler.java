import java.util.List;

public class ServerHandler implements Handler {
  private List<String> supportedMethods;

  public ServerHandler(List<String> supportedMethods) {
    this.supportedMethods = supportedMethods;
  }
  
  public Response generateResponse(Request request) {
    switch (request.getMethod()) {
      case HttpMethod.OPTIONS: 
        return new Response.Builder(HttpStatusCode.OK)
                        .setHeader(MessageHeader.ALLOW, formatAllowHeaderVal())
                        .build();
      default:
      return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED) 
                        .setHeader(MessageHeader.ALLOW, formatAllowHeaderVal())
                        .build(); 
    }
  }

  private String formatAllowHeaderVal() {
    return String.join(", ", this.supportedMethods);
  }
}