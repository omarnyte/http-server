import java.util.HashMap;
 
public class Main {
 
  public static void main(String[] args) {
    try {
      String publicDirectoryPath = args[1];
      Handler defaultHandler = new FileHandler(publicDirectoryPath);
 
      Router router = setUpRouter(defaultHandler);

      int port = Integer.parseInt(args[0]);
      Server server = new Server(port, router);
      server.start();
    } catch (ArrayIndexOutOfBoundsException e) {
      System.err.println("Plese enter a port number and a directory path as command line arguments.");
      System.err.println("Usage: java HTTPServer <port> <directory-path>");
    } catch (NonexistentDirectoryException e) {
      System.err.println("The directory " + args[1] + " does not exist.");
    } catch (NumberFormatException e) {
      System.err.println("First argument must be a number.");
    }
  }

  private static Router setUpRouter(Handler defaultHandler) {
    HashMap<String, Handler> routesMap = createRoutesMap();
    return new Router(defaultHandler, routesMap);
  }
 
  private static HashMap<String, Handler> createRoutesMap() {
    HashMap<String, Handler> routes = new HashMap<String, Handler>();
    
    String rootPath = System.getProperty("user.dir");
    routes.put("/", new RootHandler(rootPath));
    routes.put("/echo", new EchoHandler());
 
    return routes;
  }
 
}