import java.util.Map;

public class HttpStatusCode {
  public static final int OK = 200;
  public static final int CREATED = 201;
  public static final int NO_CONTENT = 204;
  public static final int SEE_OTHER = 303;
  public static final int BAD_REQUEST= 400;
  public static final int UNAUTHORIZED= 401;
  public static final int NOT_FOUND = 404;
  public static final int METHOD_NOT_ALLOWED = 405;
  public static final int UNSUPPORTED_MEDIA_TYPE = 415;
  public static final int UNPROCESSABLE_ENTITY = 422;
  public static final int INTERNAL_SERVER_ERROR = 500;

  private static final Map<Integer, String> MESSAGES = Map.ofEntries(
    Map.entry(BAD_REQUEST, "Bad Request"),
    Map.entry(CREATED, "Created"),
    Map.entry(INTERNAL_SERVER_ERROR, "Internal Server Error"),
    Map.entry(OK, "OK"),
    Map.entry(METHOD_NOT_ALLOWED, "Method Not Allowed"),
    Map.entry(NO_CONTENT, "No Content"),
    Map.entry(NOT_FOUND, "Not Found"),
    Map.entry(SEE_OTHER, "See Other"),
    Map.entry(UNPROCESSABLE_ENTITY, "Unprocessable Entity"),
    Map.entry(UNAUTHORIZED, "Unauthorized"),
    Map.entry(UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type")
  );

  public static final String getReasonPhrase(int statusCode) {
    return MESSAGES.get(statusCode);
  }

}
