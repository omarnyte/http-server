public abstract class Middleware {
  private Middleware next;

  public Middleware linkWith(Middleware next) {
    this.next = next;
    return next;
  }

  public abstract Request applyMiddleware(Request request);

  public Response applyMiddleware(Response response) {
    return response;
  };

  protected Request checkNext(Request request) {
    if (next == null) {
        return request;
    }
    return next.applyMiddleware(request);
  }

  protected Response checkNext(Response response) {
    if (next == null) {
        return response;
    }
    return next.applyMiddleware(response);
  }

}  
