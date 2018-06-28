import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class RootHandler implements Handler {

  private String jarDirectoryPath;
  private Response response;
  
  RootHandler(String jarDirectoryPath) {
    this.jarDirectoryPath = jarDirectoryPath;
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
    String[] contentsOfJARDirectory = getContentsOfJARDirectory();
    String stringifiedContentsOfJARDirectory = stringifyContentsOfDirectory(contentsOfJARDirectory);
    this.response.setMessageBody(stringifiedContentsOfJARDirectory);
  }

  private String[] getContentsOfJARDirectory() {
    File jarDirectory = new File(this.jarDirectoryPath);
    return jarDirectory.list();
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
