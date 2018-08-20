public class RequestLine {
  private String method;
  private String uri; 
  private String version;

  public RequestLine(String requestLineString) throws BadRequestException {
    try {
      String[] splitRequestLine = requestLineString.split(" ");

      String method = splitRequestLine[0];
      String uri = splitRequestLine[1];
      String[] httpVersion = splitRequestLine[2].split("/");
  
      this.method = method;
      this.uri = uri;
      this.version = httpVersion[1];
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new BadRequestException("Could not parse request line.");
    }
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
