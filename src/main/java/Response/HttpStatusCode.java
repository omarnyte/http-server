import java.util.Map;

public class HttpStatusCode {
  public static final int OK = 200;
  public static final int NOT_FOUND = 404;
  public static final int METHOD_NOT_ALLOWED = 405;

  private static final Map<Integer, String> messages = Map.ofEntries(
      Map.entry(OK, "OK"),
      Map.entry(NOT_FOUND, "Not Found"),
      Map.entry(METHOD_NOT_ALLOWED, "Method Not Allowed")
  );

  public static final String getReasonPhrase(int statusCode) {
    return messages.get(statusCode);
  }
}