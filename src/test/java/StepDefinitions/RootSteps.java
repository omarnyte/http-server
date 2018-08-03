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

  @When("^a client makes a GET request to /$")
  public void a_client_makes_a_GET_request_to() throws Throwable {
    String urlString = String.format("http://localhost:%d/", this.port);
    URL url = new URL(urlString);
    this.con = (HttpURLConnection) url.openConnection();
    
    this.responseReasonPhrase = con.getResponseMessage();
  }

  @Then("^the server should respond with the contents of the directory of where the JAR is running$")
  public void the_server_should_respond_with_the_contents_of_the_directory_of_where_the_JAR_is_running() throws Throwable {
    String messageBody = StepDefsUtil.readMessageBody(this.con);  

    String[] fileUris = {
      "/around-the-world.txt", 
      "/cat-and-dog.jpg",
      "/fresh-prince-of-bel-air.txt",
      "/git-pull.gif",
      "/git-push-force.gif",
      "/git-push.gif",
      "/purr-programming.jpg",
      "/sample-text.txt", 
      "/smiley-cat.png",
      "/software-developurr.gif"
    };

    String expectedResponseMessageBody = createExpectedHtmlFromFileUris(fileUris) + "\n";
    assertEquals(expectedResponseMessageBody, messageBody);
  }

  @Then("^the server should respond with status code (\\d+)$")
  public void the_server_should_respond_with_status_code(int statusCode) throws Throwable {
      assertEquals(statusCode, this.con.getResponseCode());
  }

  @When("^a client makes a GET request to any other endpoint$")
  public void a_client_makes_a_GET_request_to_any_other_endpoint() throws Throwable {
    String urlString = String.format("http://localhost:%d/any/other/endpoint", this.port);
    URL url = new URL(urlString);
    this.con = (HttpURLConnection) url.openConnection();
    
    this.responseReasonPhrase = con.getResponseMessage();
  }

  private String createExpectedHtmlFromFileUris(String[] uris) {
    String expectedHtml = "";
    for (String uri : uris) {
      String fileName = TestUtil.removeLeadingParenthesesFromUri(uri);
      expectedHtml += String.format(
        "<a href=\"http://localhost:%d/%s\">%s</a>" + 
        "<br>", port, fileName, fileName);
    }

    return expectedHtml;
  }

}
