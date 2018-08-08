import java.util.HashMap;

public class Request {
  private String method;
  private String uri;
  private String version;
  private HashMap<String, String> headers; 
  private HashMap<String, String> messageBody; 

  public static class Builder {
    private String method;
    private String uri;
    private String version;
    private HashMap<String, String> headers;
    private HashMap<String, String> messageBody;
    
    public Builder() {
      this.version = "1.1";
      this.headers = new HashMap<String, String>();
      this.messageBody = new HashMap<String, String>();
    }
    
    public Builder method(String method) {
      this.method = method;
      return this;
    }

    public Builder uri(String uri) {
      this.uri = uri;
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

    public Builder addMessageBodyKeyVal(String key, String value) {
      this.messageBody.put(key, value);
      return this;
    }

    public Request build() {
      return new Request(this);
    }
  }

  private Request(Builder builder) {
    this.method = builder.method;
    this.uri = builder.uri;
    this.version = builder.version;
    this.headers = builder.headers;
    this.messageBody = builder.messageBody;
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

  public String getHeader(String headerField) {
    return this.headers.get(headerField);
  }

  public HashMap<String, String> getHeaders() {
    return this.headers;
  }

  public HashMap<String, String> getMessageBody() {
    return this.messageBody;
  }

}
