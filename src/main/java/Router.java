import java.util.HashMap;

public class Router {
  private Handler defaultHandler;
  private HashMap<String, Handler> routes;

  public Router(Handler defaultHandler, HashMap<String, Handler> routes) {
    this.defaultHandler = defaultHandler;
    this.routes = routes;
  }

  public Router(HashMap<String, Handler> routes) {
    this.routes = routes;
  }

  public Response getResponse(Request request) {
    Handler handler = getHandler(request.getURI());
    return handler.generateResponse(request);
  }
  
  private Handler getHandler(String uri) {
    if (this.routes.get(uri) != null) {
      return this.routes.get(uri);
    } 

    return defaultHandler;
  }
 
} 
