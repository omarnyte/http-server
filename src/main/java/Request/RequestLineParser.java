import java.util.HashMap;

public class RequestLineParser {
  private static final String SP = " ";
  private static final String URI_QUERY_SEPARATOR_REGEX = "\\?";
  private static final String VERSION_SEPARATOR = "/";

  private String method;
  private String uri; 
  private String query;
  private String version;

  public RequestLineParser(String requestLineString) throws BadRequestException {
    try {
      String[] splitRequestLine = requestLineString.split(SP);
      String uriWithQuery = splitRequestLine[1];
      String[] splitUriWithQuery = uriWithQuery.split(URI_QUERY_SEPARATOR_REGEX);

      String method = splitRequestLine[0];
      String uri = splitUriWithQuery[0];
      String query = setQuery(splitUriWithQuery);
      String[] httpVersion = splitRequestLine[2].split(VERSION_SEPARATOR);
  
      this.method = method;
      this.uri = sanitizeUri(uri);
      this.query = query;
      this.version = httpVersion[1];
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new BadRequestException("Could not parse request line.");
    }
  }

  public String getMethod() {
    return this.method;
  }

  public String getQuery() {
    return this.query;
  }

  public String getURI() {
    return this.uri;
  }

  public String getHTTPVersion() {
    return this.version;
  }

  private String setQuery(String[] splitUriWithQuery) {
    return (splitUriWithQuery.length == 2) ? splitUriWithQuery[1] : "";
  }
  
  private String sanitizeUri(String uri) {
    String encodedSpace = "%20";
    return uri.replace(encodedSpace, " ");
  }

}
