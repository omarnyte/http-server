public class RequestLine {
  private String method;
  private String uri; 
  private String version;

  RequestLine(String requestLineString) {
    String[] splitRequestLine = requestLineString.split(" ");

    String method = splitRequestLine[0];
    String uri = splitRequestLine[1];
    String[] httpVersion = splitRequestLine[2].split("/");

    this.method = method;
    this.uri = uri;
    this.version = httpVersion[1];
  }

  public String getMethod() {
    return this.method;
  }

  public String getURI() {
    return this.uri;
  }

  public String getHTTPVersion() {
    return this.version;
  }
}