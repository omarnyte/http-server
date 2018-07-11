import java.io.File;

public class RootHandler implements Handler {
  private String directoryPath;
  private Response response;
  
  RootHandler(String directoryPath) {
    this.directoryPath = directoryPath;
    this.response = new Response();
  }
  
  public Response generateResponse(Request request) {
    setHeaders();
    setBody();
    
    return this.response;
  }

  private void setHeaders() {
    this.response.setHTTPVersion("1.1");
    this.response.setStatusCode(200);
  }

  public void setBody() {
    String[] contentsOfDirectory = getContentsOfDirectory();
    String stringifiedContentsOfDirectory = stringifyContentsOfDirectory(contentsOfDirectory);
    this.response.setMessageBody(stringifiedContentsOfDirectory);
  }

  private String[] getContentsOfDirectory() {
    File Directory = new File(this.directoryPath);
    return Directory.list();
  }

  private String stringifyContentsOfDirectory(String[] files) {
    String content = "";
    
    if (files.length == 0) {
      return "Empty directory!";
    }
    
    for (int i = 0; i < files.length; i++) {
      content += files[i] + "\n";
    }

    return content;
  }
  
}
