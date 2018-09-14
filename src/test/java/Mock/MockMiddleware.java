public class MockMiddleware extends Middleware {
  private Response expectedResponse;
  
  public MockMiddleware(Response expectedResponse) {
    this.expectedResponse = expectedResponse;
  }
  
  public Response applyMiddleware(Response response) {
    return this.expectedResponse;
  }
  
}
