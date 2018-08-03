
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.PendingException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Arrays;
import static org.junit.Assert.assertTrue;

public class ImageNegotiationSteps {
  private HttpURLConnection con;
  private String imageUri = "/cat-and-dog.jpg";
  private String publicDirectoryPath = "/Users/omard/Box Sync/8th Light/http-server/src/test/resources/testFiles";
  private String responseReasonPhrase;
  private Server server; 
  private int port = 8888;

  @When("^I send a request to the path of an image file$")
  public void i_send_a_request_to_the_path_of_an_image_file() throws Throwable {
    String urlString = String.format("http://localhost:%d%s", this.port, this.imageUri);
    URL url = new URL(urlString);
    this.con = (HttpURLConnection) url.openConnection();
    
    responseReasonPhrase = con.getResponseMessage();
  }

  @Then("^I should see the contents of the image$")
  public void i_should_see_the_contents_of_the_image() throws Throwable {
    byte[] expectedMessageBody = StepDefsUtil.readFile(this.publicDirectoryPath + this.imageUri); 
    byte[] messageBody = StepDefsUtil.readMessageBodyBytes(this.con);
    assertTrue(Arrays.equals(expectedMessageBody, messageBody));
  }
  
}
