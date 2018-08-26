import java.util.HashMap;
import java.util.Map;

public class LogFormatter {

  public String formatRequest(Request request) {
    return "[INFO]: REQUEST\n" + 
           "  Request Line: " + formatRequestLine(request) + "\n" +
           "  Request Headers:\n" + formatHeaders(request.getHeaders());
   }

  public String formatResponse(Response response) {
    return "[INFO]: RESPONSE\n" +
           "  Status Line: " + formatStatusLine(response) + "\n" +
           "  Response Headers:\n" + formatHeaders(response.getHeaders());
   }

   private String formatRequestLine(Request request) {
    String method = request.getMethod();
    String uri = request.getURI();
    String version = request.getHTTPVersion();
    return String.format("%s %s HTTP/%s", method, uri, version);
   }

   private String formatStatusLine(Response response) {
    String version = response.getHTTPVersion();
    int statusCode = response.getStatusCode();
    String reasonPhrase = response.getReasonPhrase();
    return String.format("HTTP/%s %s %s", version, statusCode, reasonPhrase);
   }

   private String formatHeaders(HashMap<String, String> headers) {
    String headersString = "";
    for (Map.Entry<String, String> entry : headers.entrySet()) {
      String field = entry.getKey();
      String value = entry.getValue();
      headersString += "  " + field + ": " + value + "\n";
    }

    return headersString;
   }
  
}
