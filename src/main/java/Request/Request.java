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

  public String getRequestLine() {
    return this.method + " " + this.uri + " HTTP/" + this.version + "\r\n";
  }

  public String toString() {
    return this.getRequestLine() + "\r\n";
  }
}


// public class Request {

//   private String method; 
//   private String uri;
//   private String version;
  
//   public void setMethod(String method) {
//     this.method = method;
//   }

//   public void setURI(String uri) {
//     this.uri = uri;
//   }

//   public void setHTTPVersion(String version) {
//     this.version = version;
//   }

//   public String getMethod() {
//     return this.method; 
//   }

//   public String getURI() {
//     return this.uri;
//   }

//   public String getHTTPVersion() {
//     return this.version;
//   }

//   public String getRequestLine() {
//     return this.method + " " + this.uri + " HTTP/" + this.version + "\r\n";
//   }

//   public String toString() {
//     return this.getRequestLine() + "\r\n";
//   }
  
// }
