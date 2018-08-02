import java.util.HashMap;
 
public class Main {
  private static CLIParser parser;
  private static DataStore store;
  private static final int DEFAULT_PORT_NUMBER = 8888;
 
  public static void main(String[] args) {
    try {
      parser = new CLIParser(args);

      DataStore store = extractDataStore();
      Handler defaultHandler = new FileHandler(store);
 
      Router router = setUpRouter(defaultHandler);
      int port = parser.getPortNumberOrDefault(DEFAULT_PORT_NUMBER);
      Server server = new Server(port, router);
      server.start();
    } catch (ArrayIndexOutOfBoundsException e) {
      System.err.println(e.getMessage());
      System.err.println("You must provide an argument for each flag.");
    } catch (MissingFlagException e) {
      System.err.println(e.getMessage());
      System.err.println("Valid store flags are: " + parser.printValidFlags());
    } catch (NonexistentDirectoryException e) {
      System.err.println(e.getMessage());
      System.err.println("Please enter a valid directory from which to serve content.");
    } catch (NumberFormatException e) {
      System.err.println("Port must be a number.");
    } catch (UnsupportedFlagException e) {
      System.err.println(e.getMessage());
      System.err.println("Valid flags are: " + parser.printValidFlags());
    }
  } 

  private static DataStore extractDataStore() throws MissingFlagException, NonexistentDirectoryException {
    String portFlag = parser.getStoreFlag();
    switch(portFlag) {
      case "-dir" :
        String publicDirectoryPath = parser.getDirectory();
        store = new Directory(publicDirectoryPath);
    } 

    return store;
  }

  private static Router setUpRouter(Handler defaultHandler) {
    HashMap<String, Handler> routesMap = createRoutesMap();
    return new Router(defaultHandler, routesMap);
  }
 
  private static HashMap<String, Handler> createRoutesMap() {
    HashMap<String, Handler> routes = new HashMap<String, Handler>();
    
    String rootPath = System.getProperty("user.dir");
    routes.put("/", new RootHandler(store));
    routes.put("/echo", new EchoHandler());
 
    return routes;
  }

}
