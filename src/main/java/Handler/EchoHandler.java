import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EchoHandler implements Handler {
  private static final String timeFormat = "hh:mm:ss";

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

  private String createMessageBody() {
    return "Hello, world: " + getFormattedTime();
  }

  private String getFormattedTime() {
    DateFormat dateFormat = new SimpleDateFormat(this.timeFormat);
    return dateFormat.format(new Date());
  }
}
