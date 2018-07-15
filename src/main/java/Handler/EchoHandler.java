import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EchoHandler implements Handler {
  
  public Response generateResponse(Request request) {
    int statusCode;
    String reasonPhrase;
    String messageBody = "";
    
    if (request.getMethod().equals("GET")) {
      statusCode = 200;
      reasonPhrase = "OK";
      messageBody = createMessageBody();
    } else {
      statusCode = 405;
      reasonPhrase = "Method Not Allowed";
    }

    return new Response.Builder()
                       .httpVersion("1.1")
                       .statusCode(statusCode)
                       .reasonPhrase(reasonPhrase)
                       .messageBody(messageBody)
                       .build();
  }

  private String createMessageBody() {
    String timeFormat = "hh:mm:ss";
    DateFormat dateFormat = new SimpleDateFormat(timeFormat);
    String formattedTime = dateFormat.format(new Date());
    return "Hello, world: " + formattedTime;
  }
}