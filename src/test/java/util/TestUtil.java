import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Date;

public class TestUtil {

  public static Request buildRequestToUri(String method, String uri) {
    return new Request.Builder() 
                      .method(method) 
                      .uri(uri) 
                      .version("1.1") 
                      .build(); 
  }
  
  public static Request buildRequestToUriWithBody(String method, String uri, String body) {
    return new Request.Builder() 
                      .method(method) 
                      .uri(uri) 
                      .version("1.1") 
                      .body(body)
                      .build(); 
  }
  
  public static String createRootHtmlFromFileNames(String[] fileNames) {
    String expectedHtml = "";
    for (String fileName : fileNames) {
      expectedHtml += String.format(
        "<a href=\"/%s\">%s</a>" + 
        "<br>", fileName, fileName);
    }

    return expectedHtml;
  }

  public static String removeLeadingParenthesesFromUri(String uri) {
    int idxOfFirstCharacter = 1;
    return uri.substring(idxOfFirstCharacter);
  }

  public static String getFormattedTime(String dateTimepattern) {
    DateFormat dateFormat = new SimpleDateFormat(dateTimepattern);
    return dateFormat.format(new Date());
  }

}
