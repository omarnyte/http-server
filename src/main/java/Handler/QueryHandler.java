public class QueryHandler implements Handler {

  public Response generateResponse(Request request) {
    switch (request.getMethod()) {
      case "GET": 
        return buildGetResponse(request);
      default: 
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED)
                           .build();
    }
  }

  private Response buildGetResponse(Request request) {
    try {
      String messageBody = createMessageBody(request.getQuery());
      return new Response.Builder(HttpStatusCode.OK) 
                         .messageBody(messageBody)
                         .build();
    } catch (ArrayIndexOutOfBoundsException e) {
      return new Response.Builder(HttpStatusCode.BAD_REQUEST)
                         .build();
    }
  }

  private String createMessageBody(String query) {
      String body = "";
      
      String[] splitQuery = query.split("&");
      for (String paramPair : splitQuery) {
        String[] splitParameters = paramPair.split("=");
        String key = splitParameters[0];
        String val = splitParameters[1];
        body += key + " : " + val + "\n";
      }
  
      return body;
  }


}