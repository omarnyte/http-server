public class Request {
  private String method;
  private String uri;
  private String version;

  public static class Builder {
    private String method;
    private String uri;
    private String version;

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

    public Request build() {
      return new Request(this);
    }
  }

  private Request(Builder builder) {
    this.method = builder.method;
    this.uri = builder.uri;
    this.version = builder.version;
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
