import cucumber.api.java.en.When;
import cucumber.api.PendingException;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;

public class RequestStepDefs {
  private final static int PORT = Integer.parseInt(System.getProperty("PORT"));
  
  private World  world;

  public RequestStepDefs(World world) { 
    this.world = world;
  }

  @When("^a client makes a GET request to /echo$")
  public void a_client_makes_a_GET_request_to_echo() throws Throwable {
    String urlString = String.format("http://localhost:%d/echo", PORT);
    URL url = new URL(urlString);
    this.world.con = (HttpURLConnection) url.openConnection();
  }

}
