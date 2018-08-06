import cucumber.api.java.en.Then;
import cucumber.api.PendingException;
import java.io.IOException;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After; 

public class ResponseStepDefs {
  private final static int PORT = Integer.parseInt(System.getProperty("PORT"));
  private final static String TEST_DIRECTORY_PATH = System.getProperty("TEST_DIRECTORY_PATH");
  
  private World world;

  public ResponseStepDefs(World world) {
    this.world = world;
  }

  @Then("^the server should respond with status code ([0-9]{3}) (.+)$")
  public void the_server_should_respond_with_status_code(int statusCode, String reasonPhrase) throws Throwable {
    assertEquals(statusCode, this.world.con.getResponseCode());
    assertEquals(reasonPhrase, this.world.con.getResponseMessage());
  }

  @Then("^the server should respond with the contents of the root path$")
  public void the_server_should_respond_with_the_contents_of_the_root_path() throws Throwable {
    String messageBody = StepDefsUtil.readMessageBody(this.world.con);  
    String[] fileNames = getFileNames();

    String expectedResponseMessageBody = TestUtil.createRootHtmlFromFileNames(fileNames) + "\n";
    assertEquals(expectedResponseMessageBody, messageBody);
  }

  @Then("^the server should respond with the current time in (.+)$")
  public void the_server_should_respond_with_the_current_time_in(String timeFormat) throws Throwable {
    String messageBody = StepDefsUtil.readMessageBody(this.world.con); 
    String currentTime = getFormattedTime(timeFormat);    

    String expectedResponseMessageBody = "Hello, world: " + currentTime+ "\n";
    assertEquals(expectedResponseMessageBody, messageBody);
  }

  @Then("^the server should respond with a message body of \"([^\"]*)\"$")
  public void the_server_should_respond_with_a_message_body_of(String expectedMessageBody) throws Throwable {
    expectedMessageBody += "\n";
    String messageBody = StepDefsUtil.readMessageBody(this.world.con);
    assertEquals(expectedMessageBody, messageBody);
  }

  @Then("^the server should response with the content of the image$")
  public void the_server_should_response_with_the_content_of_the_image() throws Throwable {
    byte[] expectedMessageBody = StepDefsUtil.readFile(TEST_DIRECTORY_PATH + this.world.requestUri); 
    byte[] messageBody = StepDefsUtil.readMessageBodyBytes(this.world.con);
    assertTrue(Arrays.equals(expectedMessageBody, messageBody));
  }

  private String getFormattedTime(String timeFormat) {
    DateFormat dateFormat = new SimpleDateFormat(timeFormat);
    return dateFormat.format(new Date());
  }

  private String[] getFileNames() {
    File testDirectory = new File(TEST_DIRECTORY_PATH);
    return testDirectory.list();
  }

}
