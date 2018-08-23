import java.io.File;
import java.io.IOException; 
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
  
public class NotFoundHandler implements Handler {   
  
  public Response generateResponse(Request request) {
    return new Response.Builder(404)
                       .messageBody(request.getURI() + " was not found!")
                       .build();
  }
}
