public class MockHandler implements Handler {
  Response response;
  
  public MockHandler(Response response) {
    this.response = response; 
  }

  public Response generateResponse(Request request) {
    return response;
  }
  
}
