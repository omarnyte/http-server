import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EchoHandler implements Handler {
  private static final String timeFormat = "hh:mm:ss";

  public Response generateResponse(Request request) {
    String method = request.getMethod();

    String statusCodeAndReasonPhrase;
    String messageBody = "";
    switch (method) {
      case "GET": 
        return buildGETResponse();
      default: 
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED)
                           .build();
    }
  }

  private Response buildGETResponse() {
    String messageBody = createMessageBody();
    int contentLength = ResponseHeader.determineContentLength(messageBody);
    return new Response.Builder(HttpStatusCode.OK)
                       .contentType("text/plain")
                       .contentLength(contentLength)
                       .messageBody(messageBody)
                       .build();
  }

  private String createMessageBody() {
    return "Hello, world: " + getFormattedTime();
  }

  private String getFormattedTime() {
    DateFormat dateFormat = new SimpleDateFormat(this.timeFormat);
    return dateFormat.format(new Date());
  }
  
}
