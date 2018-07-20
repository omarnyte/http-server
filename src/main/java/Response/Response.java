public class Response {
  private String messageBody;
  private String reasonPhrase;
  private int statusCode;
  private String version; 

  public static class Builder {
    private String messageBody;
    private String reasonPhrase;
    private int statusCode;
    private String version; 

    public Builder(int statusCode) {
      this.statusCode = statusCode;
      this.reasonPhrase = HttpStatusCode.getReasonPhrase(statusCode);
      this.messageBody = "";
      this.version = "1.1";
    }

    public Builder httpVersion(String version) {
      this.version = version;
      return this;
    }

    public Builder statusCode(int statusCode) {
      this.statusCode = statusCode;
      return this;
    }

    public Builder reasonPhrase(String reasonPhrase) {
      this.reasonPhrase = reasonPhrase;
      return this;
    }
    
    public Builder messageBody(String messageBody) {
      this.messageBody = messageBody;
      return this;
    }

    public Response build() {
      return new Response(this);
    }
    
  }
  
  private Response(Builder builder) {
    this.version = builder.version;
    this.statusCode = builder.statusCode;
    this.reasonPhrase = builder.reasonPhrase;
    this.messageBody = builder.messageBody;
  }

  public String getHTTPVersion() {
    return this.version;
  }  

  public int getStatusCode() {
    return this.statusCode;
  }  

  public String getReasonPhrase() {
    return this.reasonPhrase;
  }

  public String getMessageBody() {
    return this.messageBody;
  }
  public String toString() {
    return getStatusLine() + "\r\n" + this.messageBody;
  }
  
  private String getStatusLine() {
    return "HTTP/" + this.version + " " + this.statusCode + " " + this.reasonPhrase + "\r\n";
  }

}
