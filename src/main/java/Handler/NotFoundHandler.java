public class NotFoundHandler implements Handler {  
  public Response generateResponse(Request request) {    
    return new Response.Builder(HttpStatusCode.NOT_FOUND)
                       .httpVersion("1.1")
                       .build();
  }

}
