import java.io.File;
import java.io.IOException; 
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
  
public class FormHandler implements Handler {   
  private Directory directory;
  
  public FormHandler(Directory directory) {
    this.directory = directory;
  }
  
  public Response generateResponse(Request request) { 
    String uri = request.getURI();
     
    switch (request.getMethod()) { 
      case "POST":  
        return buildPostResponse(request); 
      default:  
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED) 
                           .build(); 
    } 
  }

  private Response buildPostResponse(Request request) {
    try {
      String uri = "/POSTed/" + randomFileName() + ".txt";
      byte[] content = createFileContent(request);
      directory.createFileWithContent(uri, content);
      return new Response.Builder(HttpStatusCode.SEE_OTHER)
                         .setHeader(MessageHeader.LOCATION, uri)
                         .build();
    } catch(ArrayIndexOutOfBoundsException e) {
      return new Response.Builder(HttpStatusCode.BAD_REQUEST)
                         .build();
    }
  }

   private String randomFileName() {
    int rand = new Random().nextInt(999999999) + 100000000;
    return Integer.toString(rand);
  }

  private byte[] createFileContent(Request request) throws ArrayIndexOutOfBoundsException {
    String content =  "Thank you for submitting the following data:\n";
    HashMap<String, String> bodyKeyValPairs = extractKeyValPairsFromBody(request.getBody());
    content += stringifyKeyValPairs(bodyKeyValPairs);
    return content.getBytes();
  }
  
  private HashMap<String, String> extractKeyValPairsFromBody(String body) throws ArrayIndexOutOfBoundsException {
    HashMap<String, String> keyValPairs = new HashMap<String, String>();
    String[] splitBody = body.split("&");
    for (String keyValPair : splitBody) {
      String[] splitKeyValPair = keyValPair.split("=");
      String key = splitKeyValPair[0];
      String val = splitKeyValPair[1];
      keyValPairs.put(key, val);
    }
    
    return keyValPairs;
  }

  private String stringifyKeyValPairs(HashMap<String, String> keyValPairs) {
    String result = "";
    for (Map.Entry<String, String> entry : keyValPairs.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      result += key + ": " + value + "\n";
    }

    return result;
  }

}
