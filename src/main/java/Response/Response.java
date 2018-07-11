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

    public Builder() {
      this.messageBody = "";
      this.reasonPhrase = "";
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
    return this.getStatusLine() + "\r\n" + this.messageBody;
  }
  
}
