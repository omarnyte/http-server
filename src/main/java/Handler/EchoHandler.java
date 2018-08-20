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
      case "HEAD": 
        return buildHeadResponse();
      case "GET": 
        return buildGetResponse();
      default: 
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED)
                           .build();
    }
  }

  private Response buildHeadResponse() {
    String messageBody = createMessageBody();
    int contentLength = MessageHeader.determineContentLength(messageBody);
    return new Response.Builder(HttpStatusCode.OK)
                       .setHeader(MessageHeader.CONTENT_TYPE, "text/plain")
                       .setHeader(MessageHeader.CONTENT_LENGTH, contentLength)
                       .build();
  }

  private Response buildGetResponse() {
    String messageBody = createMessageBody();
    int contentLength = MessageHeader.determineContentLength(messageBody);
    return new Response.Builder(HttpStatusCode.OK)
                       .setHeader(MessageHeader.CONTENT_TYPE, "text/plain")
                       .setHeader(MessageHeader.CONTENT_LENGTH, contentLength)
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
