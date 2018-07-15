import java.io.File;

public class RootHandler implements Handler {
  private String directoryPath;
  private Response response;
  
  public RootHandler(String directoryPath) {
    this.directoryPath = directoryPath;
  }
  
  public Response generateResponse(Request request) {
    String method = request.getMethod();

    ResponseConstructor constructor;
    switch (method) {
      case "GET": 
        constructor = new ResponseConstructor(200, createMessageBody());
        break;
      default: 
        constructor = new ResponseConstructor(405);
    }

    return constructor.constructResponse();
  }

  public String createMessageBody() {
    String[] contentsOfDirectory = getContentsOfDirectory();
    return stringifyContentsOfDirectory(contentsOfDirectory);
  }

  private String[] getContentsOfDirectory() {
    File directory = new File(this.directoryPath);
    return directory.list();
  }

  private String stringifyContentsOfDirectory(String[] fileNames) {
    String content = "";
    
    if (fileNames.length == 0) {
      return "Empty directory!";
    }
    
    for (String fileName : fileNames) {
      content += fileName + "\n";
    }
    
    return content;
  }
  
}
