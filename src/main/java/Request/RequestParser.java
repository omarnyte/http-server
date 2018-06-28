public class RequestParser {
  private String method;
  private RequestLine requestLine;
  private String[] splitRequest;
  private String uri;
  private String version;

  RequestParser(String requestString) {
    this.splitRequest = requestString.split("\r\n");
    parseRequestLine();
  }

  private void parseRequestLine() {
    String requestLineString = this.splitRequest[0];
    this.requestLine = new RequestLine(requestLineString);
  }

  public Request generateRequest() {
    Request request = new Request.Builder()
                                 .method(this.requestLine.getMethod())
                                 .uri(this.requestLine.getURI())
                                 .version(this.requestLine.getHTTPVersion())
                                 .build();
    return request;
  }

}
