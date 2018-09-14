import java.util.List;

public class ServerHandler implements Handler {
  private List<String> supportedMethods;

  public ServerHandler(List<String> supportedMethods) {
    this.supportedMethods = supportedMethods;
  }
  
  public Response generateResponse(Request request) {
    switch (request.getMethod()) {
      case HttpMethod.OPTIONS: 
        return ResponseUtil.buildOptionsResponse(this.supportedMethods);
      default:
        return ResponseUtil.buildMethodNotAllowedResponse(this.supportedMethods);
    }
  }

}
