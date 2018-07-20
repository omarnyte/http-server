import java.util.HashMap;

public class ResponseConstructor {
  private String messageBody;
  private String reasonPhrase;
  private int statusCode;
  private HashMap<Integer, String> statusReasonMap = createStatusReasonMap();

  public ResponseConstructor(int statusCode) {
    this.statusCode = statusCode;
    this.reasonPhrase = statusReasonMap.get(this.statusCode);
    this.messageBody = "";
  }

  public ResponseConstructor(int statusCode, String messageBody) {
    this.statusCode = statusCode;
    this.reasonPhrase = statusReasonMap.get(this.statusCode);
    this.messageBody = messageBody;
  }

  public Response constructResponse() {
    return new Response.Builder()
                       .httpVersion("1.1")
                       .statusCode(this.statusCode)
                       .reasonPhrase(this.reasonPhrase)
                       .messageBody(this.messageBody)
                       .build();
  }

  private HashMap<Integer, String> createStatusReasonMap() {
    HashMap<Integer, String> map = new HashMap<Integer, String>();
    map.put(200, "OK");
    map.put(405, "Method Not Allowed");
    return map;
  }
}