public class TestUtil {

  public static String createRootHtmlFromUris(String[] uris) {
    String expectedHtml = "";
    for (String uri : uris) {
      String fileName = TestUtil.removeLeadingParenthesesFromUri(uri);
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

  

}
