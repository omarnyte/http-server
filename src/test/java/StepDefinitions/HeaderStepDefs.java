import cucumber.api.java.en.Then;
import cucumber.api.PendingException;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HeaderStepDefs {
  private World world;

  public HeaderStepDefs(World world) {
    this.world = world;
  }

  @Then("^the server should respond with the header (.+) (.+)$")
  public void the_server_should_respond_with_the_header(String field, String expectedValue) throws Throwable {
    assertEquals(expectedValue, this.world.con.getHeaderField(field));
  }

  @Then("^the response should contain the header (.+)$")
  public void the_response_should_contain_the_header_Location(String field) throws Throwable {
    assertFalse(this.world.con.getHeaderField(field) == null);
  }

}
