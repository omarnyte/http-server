public class Response {

  private String messageBody = "";
  private String reasonPhrase = "";
  private int statusCode;
  private String version; 

  public void setHTTPVersion(String version) {
    this.version = version;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public void setReasonPhrase(String reasonPhrase) {
    this.reasonPhrase = reasonPhrase;
  }

  public void setMessageBody(String messageBody) {
    this.messageBody = messageBody;
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

  public String getStatusLine() {
    return "HTTP/" + this.version + " " + this.statusCode + " " + this.reasonPhrase + "\r\n";
  }

  public String toString() {
    return this.getStatusLine() + "\r\n" + this.messageBody;
  }
  
}
