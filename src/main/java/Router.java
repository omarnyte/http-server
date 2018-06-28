import java.util.HashMap;

public class Router {

  private HashMap<String, Handler> routes;

  Router(HashMap<String, Handler> routes) {
    this.routes = routes;
  }
  
  public Handler getHandler(String uri) {
    if (this.routes.get(uri) != null) {
      return this.routes.get(uri);
    } 

    return new NotFoundHandler();
  }
 
} 
