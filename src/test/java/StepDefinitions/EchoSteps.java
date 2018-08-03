import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.PendingException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.After; 

public class EchoSteps {
  private HttpURLConnection con;
  private String publicDirectoryPath = "/Users/omard/Box Sync/8th Light/http-server/src/test/resources/testFiles";
  private String responseMessageBody;
  private Server server; 
  private String timeFormat = "hh:mm:ss";
  private int port = 8888;

  @When("^a client makes a GET request to /echo$")
  public void a_client_makes_a_GET_request_to_echo() throws Throwable {
    String urlString = String.format("http://localhost:%d/echo", this.port);
    URL url = new URL(urlString);
    this.con = (HttpURLConnection) url.openConnection();
    
    responseMessageBody = con.getResponseMessage();
  }

  @Then("^the server should respond with \"([^\"]*)\"$")
  public void the_server_should_respond_with(String arg1) throws Throwable {
    String messageBody = StepDefsUtil.readMessageBody(this.con); 
    String currentTime = getFormattedTime();    

    String expectedResponseMessageBody = "Hello, world: " + getFormattedTime() + "\n";
    assertEquals(expectedResponseMessageBody, messageBody);
  }

  private String getFormattedTime() {
    DateFormat dateFormat = new SimpleDateFormat(this.timeFormat);
    return dateFormat.format(new Date());
  }

}
