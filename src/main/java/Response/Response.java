import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class Response {
  private String messageBody;
  private String reasonPhrase;
  private int statusCode;
  private String version;
  private HashMap<String, String> headers;

  public static class Builder { 
    private HashMap<String, String> headers;
    private String messageBody;
    private String reasonPhrase;
    private int statusCode;
    private String version;

    public Builder(int statusCode) {
      this.statusCode = statusCode;
      this.reasonPhrase = HttpStatusCode.getReasonPhrase(statusCode);
      this.messageBody = "";
      this.version = "1.1";
      this.headers = new HashMap<String, String>();
    }

    public Builder httpVersion(String version) {
      this.version = version;
      return this;
    }

    public Builder statusCode(int statusCode) {
      this.statusCode = statusCode;
      return this;
    }

    public Builder reasonPhrase(String reasonPhrase) {
      this.reasonPhrase = reasonPhrase;
      return this;
    }
    
    public Builder messageBody(String messageBody) {
      this.messageBody = messageBody;
      return this;
    }

    public Builder setHeader(String headerField, String value) {
      this.headers.put(headerField, value);
      return this;
    }

    public Builder setHeader(String headerField, int value) {
      String stringifiedValue = Integer.toString(value);
      this.headers.put(headerField, stringifiedValue);
      return this;
    }

    // delete after merging ft/image-content-negotiation with master vvv
    public Builder contentType(String contentType) {
      this.headers.put(ResponseHeader.CONTENT_TYPE, contentType);
      return this;
    }

    public Builder contentLength(int contentLength) {
      String stringifedContentLength = Integer.toString(contentLength);
      this.headers.put(ResponseHeader.CONTENT_LENGTH, stringifedContentLength);
      return this;
    }
    // delete after merging ft/image-content-negotiation with master  ^^^

    public Response build() {
      return new Response(this);
    }
    
  }
  
  private Response(Builder builder) {
    this.version = builder.version;
    this.statusCode = builder.statusCode;
    this.reasonPhrase = builder.reasonPhrase;
    this.messageBody = builder.messageBody;
    this.headers = builder.headers;
  }

  public String getHTTPVersion() {
    return this.version;
  }  

  public int getStatusCode() {
    return this.statusCode;
  }  

  public String getReasonPhrase() {
    return this.reasonPhrase;
  }

  public String getMessageBody() {
    return this.messageBody;
  }

  public String getHeader(String headerField) {
    return this.headers.get(headerField);
  }

  public HashMap getHeaders() {
    return this.headers;
  }

  // delete after merging ft/image-content-negotiation with master  vvvvv
  public String getContentType() {
    return this.headers.get(ResponseHeader.CONTENT_TYPE);
  }

  public int getContentLength() {
    String stringifiedContentLength = this.headers.get(ResponseHeader.CONTENT_LENGTH);
    return Integer.parseInt(stringifiedContentLength);
  }
  // delete after merging ft/image-content-negotiation with master  ^^^

}
