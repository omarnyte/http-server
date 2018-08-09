import java.io.File;
import java.io.IOException; 
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
  
public class FormHandler implements Handler {   
  private DataStore store;
  
  public FormHandler(DataStore store) {
    this.store = store;
  }
  
  public Response generateResponse(Request request) { 
    String uri = request.getURI();
     
    switch (request.getMethod()) { 
      case "POST":  
        return buildPostResponse(); 
      default:  
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED) 
                           .build(); 
    } 
  }

  private Response buildPostResponse(Request request) {
      String uri = "/POSTed/" + randomFileName() + ".txt";
      byte[] content = createFileContent(request);
      store.postFile(uri, content);
      return new Response.Builder(HttpStatusCode.SEE_OTHER)
                         .setHeader("Location", redirectUri)
                         .build();
    } catch (IOException e) {
      System.err.println(e);
      return new Response.Builder(HttpStatusCode.BAD_REQUEST).build();
    }
  }

  private String randomFileName() {
    int rand = new Random().nextInt(999999999) + 100000000;
    return Integer.toString(rand);
  }

  private byte[] createFileContent(Request request) {
    String LINE_SEPARATOR = System.getProperty("line.separator");
    String content =  "Thank you for submitting the following data:\n";
    HashMap<String, String> messageBody = request.getMessageBody();
    for (Map.Entry<String, String> entry : messageBody.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      content += key + " : " + value + "\n";
    }

    return content.getBytes();
  }

}