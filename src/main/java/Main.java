import java.util.HashMap;
 
public class Main {
  private static CLIParser parser;
  private static DataStore store;
  private static final int DEFAULT_PORT_NUMBER = 7777;
 
  public static void main(String[] args) {
    try {
      parser = new CLIParser(args);

      DataStore store = extractDataStore();
      Handler defaultHandler = new FileHandler(store);
 
      Router router = setUpRouter(defaultHandler);
      int port = extractPortNumber();
      Server server = new Server(port, router);
      server.start();
    } catch (ArrayIndexOutOfBoundsException e) {
      System.err.println(e.getMessage());
      System.err.println("You must provide an argument for each flag.");
    } catch (NonexistentDirectoryException e) {
      System.err.println(e.getMessage());
      System.err.println("Please enter a valid directory from which to serve content.");
    } catch (NullPointerException e) {
      System.err.println("You must provide a data store to start the server.");
      System.err.println("Usage: java -jar http-server.jar -port <port> -<store-flag> <store argument>");
      printAvailableStores();
    } catch (NumberFormatException e) {
      System.err.println("Port must be a number.");
    }
  }

  private static DataStore extractDataStore() throws NonexistentDirectoryException {
    String portFlag = parser.getStoreFlag();
    switch(portFlag) {
      case "-dir" :
        String publicDirectoryPath = parser.getDirectory();
        store = new Directory(publicDirectoryPath);
    } 
    
    return store;
  }

  private static int extractPortNumber() {
    return parser.containsPortFlag() ? parser.getPortNumber() : DEFAULT_PORT_NUMBER;
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

  private static void printAvailableStores() {
    System.out.println("Available stores are: -dir directory");
  }
 
}