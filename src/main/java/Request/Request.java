import java.util.HashMap;

public class Request {
  private String method;
  private String uri;
  private String query; 
  private String version;
  private HashMap<String, String> headers; 
  private String body; 

  public static class Builder {
    private String method;
    private String uri;
    private String query;
    private String version;
    private HashMap<String, String> headers;
    private String body; 
    
    public Builder() {
      this.query = "";
      this.version = "1.1";
      this.headers = new HashMap<String, String>();
      this.body = "";
    }
    
    public Builder method(String method) {
      this.method = method;
      return this;
    }

    public Builder uri(String uri) {
      this.uri = uri;
      return this;
    }

    public Builder query(String query) {
      this.query = query;
      return this;
    }

    public Builder version(String version) {
      this.version = version;
      return this;
    }

    public Builder setHeader(String headerField, String value) {
      this.headers.put(headerField, value);
      return this;
    }

    public Builder headers(HashMap<String, String> headers) {
      this.headers = headers;
      return this;
    }

    public Builder body(String body) {
      this.body = body;
      return this;
    }

    public Request build() {
      return new Request(this);
    }
  }

  private Request(Builder builder) {
    this.method = builder.method;
    this.uri = builder.uri;
    this.query = builder.query;
    this.version = builder.version;
    this.headers = builder.headers;
    this.body = builder.body;
  }

    public String getMethod() {
    return this.method; 
  }

  public String getURI() {
    return this.uri;
  }

  public String getQuery() {
    return this.query;
  }

  public String getHTTPVersion() {
    return this.version;
  }

  public String getHeader(String headerField) {
    return this.headers.get(headerField);
  }

  public HashMap<String, String> getHeaders() {
    return this.headers;
  }

  public String getBody() {
    return this.body;
  }

}
