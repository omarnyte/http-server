<<<<<<< HEAD
import java.util.HashMap;
import java.util.Map;
=======
import java.io.ByteArrayOutputStream;
import java.io.IOException;
>>>>>>> Read files using byte[] rather than String, allowing responses to media other than text

public class ResponseFormatter {
  Response response;
  
  public ResponseFormatter(Response response) {
    this.response = response;
  }
  
  public byte[] formatResponse() {
    ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
      try {
        responseStream.write(formatStatusLine().getBytes());
        responseStream.write(formatHeaders().getBytes());
        responseStream.write("\r\n".getBytes());
        responseStream.write(this.response.getMessageBody());
      } catch (IOException e) {
        System.err.println("Could not format response.");
        e.printStackTrace();
      }

      return responseStream.toByteArray();
  }

  private String formatStatusLine() {
    return formatHttpVersion() + " " + this.response.getStatusCode() + " " + this.response.getReasonPhrase() + "\r\n";
  }

  private String formatHttpVersion() {
    return "HTTP/" + this.response.getHTTPVersion();
  }

  private String formatHeaders() {
    String formattedHeaders = "";
    HashMap<String, String> headersMap = this.response.getHeaders();
    for (Map.Entry<String, String> fieldValuePair : headersMap.entrySet()) {
      String field = fieldValuePair.getKey();
      Object value = fieldValuePair.getValue();
      formattedHeaders += field + ": " + value + "\r\n";
    }
    
    return formattedHeaders;
  }

}
