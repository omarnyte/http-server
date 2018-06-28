public class NotFoundHandler implements Handler {
  private Response response = new Response();
  
  public Response generateResponse(Request request) {    
    setHeaders();

    return this.response;
  }

  private void setHeaders() {
    this.response.setHTTPVersion("1.1");
    this.response.setStatusCode(404);
  }

}
