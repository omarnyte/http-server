
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

public class TextContentNegotiationSteps {
  private HttpURLConnection con;
  private String publicDirectoryPath = "/Users/omard/Box Sync/8th Light/http-server/src/test/resources/testFiles";
  private String responseReasonPhrase;
  private Server server; 
  private int port = 8888;
  
  @Given("^the server is running$")
  public void the_server_is_running() throws Throwable {
    String[] args = { Integer.toString(this.port), publicDirectoryPath };
    Main.main(args);
  }

  @When("^I send a request to the path of a text file$")
  public void i_send_a_request_to_the_path_of_a_text_file() throws Throwable {
    String urlString = String.format("http://localhost:%d/sample-text.txt", this.port);
    URL url = new URL(urlString);
    this.con = (HttpURLConnection) url.openConnection();
    
    responseReasonPhrase = con.getResponseMessage();
  }

  @When("^that text file exists in the directory$")
  public void that_text_file_exists_in_the_directory() throws Throwable {

  }

  @Then("^I should see the contents of the file$")
  public void i_should_see_the_contents_of_the_file() throws Throwable {
      String messageBody = readMessageBody();
      assertEquals("This is a sample text file.", messageBody);
  }


  @When("^that text file does not exist in the directory$")
  public void that_text_file_does_not_exist_in_the_directory() throws Throwable {
    String urlString = String.format("http://localhost:%d/does-not-exist.txt", this.port);
    URL url = new URL(urlString);
    this.con = (HttpURLConnection) url.openConnection();
    
    this.responseReasonPhrase = con.getResponseMessage();
  }

  @Then("^I should get a (\\d+) Not Found response$")
  public void i_should_get_a_Not_Found_response(int arg1) throws Throwable {
      assertEquals("Not Found", this.responseReasonPhrase);
  }

  private String readMessageBody() throws IOException {
    BufferedReader in = new BufferedReader(
      new InputStreamReader(this.con.getInputStream()));
    String inputLine;
    String messageBody = "";
    while ((inputLine = in.readLine()) != null) {
        messageBody += inputLine;
    }
    in.close();

    return messageBody;
  }
}