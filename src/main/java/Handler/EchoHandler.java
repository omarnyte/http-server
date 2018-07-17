import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EchoHandler implements Handler {
  private static final String timeFormat = "hh:mm:ss";

  public Response generateResponse(Request request) {
    String method = request.getMethod();
    ResponseConstructor constructor;

    String statusCodeAndReasonPhrase;
    String messageBody = "";
    switch (method) {
      case "GET": 
        return new Response.Builder(StatusPhrase.OK)
                           .messageBody(createMessageBody())
                           .build();
      default: 
        return new Response.Builder(StatusPhrase.METHOD_NOT_ALLOWED)
                           .build();
    }
  }

  private String createMessageBody() {
    return "Hello, world: " + getFormattedTime();
  }

  private String getFormattedTime() {
    DateFormat dateFormat = new SimpleDateFormat(this.timeFormat);
    return dateFormat.format(new Date());
  }
}
