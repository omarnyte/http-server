import java.util.HashMap;

public class HTTPServer {
  
  public static void main(String[] args) {
    try {
      HashMap<String, Handler> routesMap = createRoutesMap();
      Router router = new Router(routesMap);
      
      int port = Integer.parseInt(args[0]);
      Server server = new Server(port, router);
      server.start();
    } catch (ArrayIndexOutOfBoundsException e) {
      System.err.println("Plese enter a port number as a command line argument.");
      System.err.println("Usage: java HTTPServer <port>");
    } catch (NumberFormatException e) {
      System.err.println("Argument must be a number.");
    } 
  }

  public static HashMap<String, Handler> createRoutesMap() {
    HashMap<String, Handler> routes = new HashMap<String, Handler>();
    
    String rootPath = System.getProperty("user.dir");
    routes.put("/", new RootHandler(rootPath));

    return routes;
  }

}
