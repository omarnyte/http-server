import java.util.Map;

public class HttpStatusCode {
  public static final int OK = 200;
  public static final int CREATED = 201;
  public static final int NO_CONTENT = 204;
  public static final int SEE_OTHER = 303;
  public static final int BAD_REQUEST= 400;
  public static final int NOT_FOUND = 404;
  public static final int METHOD_NOT_ALLOWED = 405;
  public static final int INTERNAL_SERVER_ERROR = 500;

  private static final Map<Integer, String> messages = Map.ofEntries(
    Map.entry(BAD_REQUEST, "Bad Request"),
    Map.entry(CREATED, "Created"),
    Map.entry(INTERNAL_SERVER_ERROR, "Internal Server Error"),
    Map.entry(OK, "OK"),
    Map.entry(METHOD_NOT_ALLOWED, "Method Not Allowed"),
    Map.entry(NO_CONTENT, "No Content"),
    Map.entry(NOT_FOUND, "Not Found"),
    Map.entry(SEE_OTHER, "See Other")
  );

  public static final String getReasonPhrase(int statusCode) {
    return messages.get(statusCode);
  }

}
