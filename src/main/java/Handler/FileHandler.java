import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileReader; 
import java.io.IOException; 
 
public class FileHandler implements Handler { 
  String directoryPath; 
  String filePath; 
  Request request; 
 
  public FileHandler(String directoryPath) throws NonexistentDirectoryException { 
    File directory = new File(directoryPath);
    if (directory.exists()) {
      this.directoryPath = directoryPath; 
    } else {
      String errorMessage = this.directoryPath + " does not exist. Enter a valid directory.";
      throw new NonexistentDirectoryException(errorMessage);
    }
  } 
   
  public Response generateResponse(Request request) { 
    this.request = request; 
     
    switch (request.getMethod()) { 
      case "GET":  
        return buildGETResponse(); 
      default:  
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED) 
                           .build(); 
    } 
  } 
 
  private Response buildGETResponse() { 
    int statusCode = determineStatusCode();
    String messageBody = createMessageBody(statusCode);
     
    return new Response.Builder(statusCode) 
                       .messageBody(messageBody) 
                       .build(); 
  } 
 
  private int determineStatusCode() { 
    File file = createFileFromURI(); 
    return file.exists() ? HttpStatusCode.OK : HttpStatusCode.NOT_FOUND; 
  } 
 
  private File createFileFromURI() { 
    this.filePath = this.directoryPath + request.getURI(); 
    return new File(this.filePath); 
  } 
  
  private String createMessageBody(int statusCode) {
    String emptyMessageBody = "";
    return (statusCode == HttpStatusCode.OK) ? stringifyFileContent() : emptyMessageBody;
  }
 
  private String stringifyFileContent() { 
    String content = ""; 
     
    try { 
      BufferedReader reader = new BufferedReader(new FileReader(this.filePath)); 
      String line; 
      while ((line = reader.readLine()) != null) { 
        content += line + "\n"; 
      } 
    } catch(IOException e) { 
      e.printStackTrace(); 
    } 
 
    return content; 
  } 
 
}
