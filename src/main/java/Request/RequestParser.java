public class RequestParser {
  private String method;
  private RequestLine requestLine;
  private String[] splitRequest;
  private String uri;
  private String version;

  public RequestParser(String requestString) {
    this.splitRequest = requestString.split("\r\n");
  }
  
  public Request generateRequest() throws BadRequestException {
    parseRequestLine();
    return new Request.Builder()
                      .method(this.requestLine.getMethod())
                      .uri(this.requestLine.getURI())
                      .version(this.requestLine.getHTTPVersion())
                      .build();
  }

  private void parseRequestLine() throws BadRequestException {
    try {
      String requestLineString = this.splitRequest[0];
      this.requestLine = new RequestLine(requestLineString);
    } catch (Exception e) {
      throw new BadRequestException("Bad request!");
    }
  }

}
