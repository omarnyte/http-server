public class NotFoundHandler implements Handler {  
  public Response generateResponse(Request request) {    
    return new Response.Builder()
                       .httpVersion("1.1")
                       .statusCode(404)
                       .reasonPhrase("Not Found")
                       .build();
  }

}
