import cucumber.api.java.en.Then;
import cucumber.api.PendingException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.After; 

public class ResponseStepDefs {
  private final static int PORT = Integer.parseInt(System.getProperty("PORT"));
  private final static String TEST_DIRECTORY_PATH = System.getProperty("TEST_DIRECTORY_PATH");
  
  private World world;

  public ResponseStepDefs(World world) {
    this.world = world;
  }

  @Then("^the server should respond with status code ([0-9]{3}) (.+)$")
  public void the_server_should_respond_with_status_code_OK(int statusCode, String reasonPhrase) throws Throwable {
    assertEquals(statusCode, this.world.con.getResponseCode());
    assertEquals(reasonPhrase, this.world.con.getResponseMessage());
  }


  @Then("^the server should respond with \"([^\"]*)\"$")
  public void the_server_should_respond_with(String arg1) throws Throwable {
    String messageBody = StepDefsUtil.readMessageBody(this.world.con); 
    String currentTime = getFormattedTime();    

    String expectedResponseMessageBody = "Hello, world: " + getFormattedTime() + "\n";
    assertEquals(expectedResponseMessageBody, messageBody);
  }

  private String getFormattedTime() {
    DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
    return dateFormat.format(new Date());
  }

}
