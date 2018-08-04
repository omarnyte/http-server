import java.util.HashMap;
import java.util.Map;

public class ResponseFormatter {
  Response response;
  
  public ResponseFormatter(Response response) {
    this.response = response;
  }
  
  public String formatResponse() {
    return formatStatusLine() + 
           formatHeaders() + 
           "\r\n" +
           this.response.getMessageBody();
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
