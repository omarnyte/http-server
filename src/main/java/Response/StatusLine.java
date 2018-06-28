public class StatusLine {

  private String httpVersion = "HTTP/1.1";
  private String code;
  private String reasonPhrase = "";
  
  StatusLine(int code) {
    this.code = Integer.toString(code); 
  }

  StatusLine(int code, String reasonPhrase) {
    this.code = Integer.toString(code); 
    this.reasonPhrase = reasonPhrase;
  }

  public String createStatusLine() {
    return httpVersion + " " + code + " " + reasonPhrase + "\r\n";
  }
}