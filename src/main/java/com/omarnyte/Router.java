package com.omarnyte;

import java.util.HashMap;

import com.omarnyte.exception.NonexistentDirectoryException;
import com.omarnyte.handler.DirectoryHandler;
import com.omarnyte.handler.FileHandler;
import com.omarnyte.handler.Handler;
import com.omarnyte.handler.PatchHandler;
import com.omarnyte.jsonpatch.JsonPatchParser;
import com.omarnyte.request.Request;
import com.omarnyte.response.Response;

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
    if (request.getMethod().equals("PATCH")) handler = new PatchHandler(this.directory, new JsonPatchParser());
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
