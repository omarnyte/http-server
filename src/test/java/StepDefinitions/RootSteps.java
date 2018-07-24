import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.PendingException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import static org.junit.Assert.assertEquals;
import org.junit.After; 

public class RootSteps {
  private HttpURLConnection con;
  private String responseReasonPhrase;
  private Server server;
  private int port = 8888; 

  @Then("^the server should respond with the contents of the directory of where the JAR is running$")
  public void the_server_should_respond_with_the_contents_of_the_directory_of_where_the_JAR_is_running() throws Throwable {
    String messageBody = readMessageBody();  

    String expectedResponseMessageBody = "around-the-world.txt\n" + 
                                         "fresh-prince-of-bel-air.txt\n" +
                                         "sample-text.txt\n" ;
    assertEquals(expectedResponseMessageBody, messageBody);
  }

  @Then("^the server should respond with status code (\\d+)$")
  public void the_server_should_respond_with_status_code(int statusCode) throws Throwable {
      assertEquals(statusCode, this.con.getResponseCode());
  }

  @When("^a client makes a GET request to /(.*)$")
  public void a_client_makes_a_GET_request_to(String uri) throws Throwable {
    String urlString = String.format("http://localhost:%d/%s", this.port, uri);
    URL url = new URL(urlString);
    this.con = (HttpURLConnection) url.openConnection();
    
    this.responseReasonPhrase = con.getResponseMessage();
  }

  private String readMessageBody() throws IOException {
    BufferedReader in = new BufferedReader(
      new InputStreamReader(this.con.getInputStream()));
    String inputLine;
    String messageBody = "";
    while ((inputLine = in.readLine()) != null) {
        messageBody += inputLine + "\n";
    }
    in.close();

    return messageBody;
  }
}


