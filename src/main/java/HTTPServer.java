import java.io.*;
import java.net.*;
import java.util.HashMap;


public class HTTPServer {
  
  public static void main(String[] args) {
    try {
      HashMap<String, Handler> routesMap = createRoutesMap();
      Router router = new Router(routesMap);
      
      int port = Integer.parseInt(args[0]);
      Server server = new Server(port, router);
      server.start();
    } 
    catch (Exception e) {
      System.err.println(e);
      System.err.println("Usage: java HTTPServer <port>");
    }
  }

  public static HashMap<String, Handler> createRoutesMap() {
    HashMap<String, Handler> routes = new HashMap<String, Handler>();
    
    String rootPath = System.getProperty("user.dir");
    routes.put("/", new RootHandler(rootPath));

    return routes;
  }

}
