import cucumber.api.java.en.When;
import cucumber.api.PendingException;
import java.util.Base64;

public class RequestHeaderStepDefs {
  private World  world;

  public RequestHeaderStepDefs(World world) { 
    this.world = world;
  }
  
  @When("^the client provides the credentials: (.+) (.+)$")
  public void the_client_provides_the_credentials(String username, String password) throws Throwable {
    String field = "Basic " + encodeCredentials(username, password);
    this.world.con.setRequestProperty("Authorization", field);
  }

  private String encodeCredentials(String username, String password) {
    String raw = username + ":" + password;
    Base64.Encoder encoder = Base64.getEncoder(); 
    return new String(encoder.encode(raw.getBytes()));
  }

}