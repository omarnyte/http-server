import java.util.HashMap;

public class Router {
  private Handler defaultHandler;
  private HashMap<String, Handler> routes;
  private Directory directory;

  public Router(Handler defaultHandler, HashMap<String, Handler> routes, Directory directory) {
    this.defaultHandler = defaultHandler;
    this.routes = routes;
    this.directory = directory;
  }

  public Response getResponse(Request request) {
    Handler handler = getHandler(request.getURI());
    return handler.generateResponse(request);
  }
  
  private Handler getHandler(String uri) {
    try {
      if (this.routes.get(uri) != null) {
        return this.routes.get(uri);
      } else if (this.directory.isDirectory(uri)) {
        Directory subdirectory = this.directory.createSubdirectory(uri);
        return new DirectoryHandler(subdirectory, uri);
      } else if (this.directory.isFile(uri)) {
        return new FileHandler(this.directory);
      }
    } catch(NonexistentDirectoryException e) {
      System.err.println(e);
    }

    return defaultHandler;
  }
 
} 
