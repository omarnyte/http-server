import cucumber.api.java.en.When;
import cucumber.api.PendingException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;

public class RequestStepDefs {
  private final static int PORT = Integer.parseInt(System.getProperty("PORT"));
  
  private World  world;

  public RequestStepDefs(World world) { 
    this.world = world;
  }

  @When("^a client makes a ([A-Z]+) request to (.+)$")
  public void a_client_makes_a_HEAD_request_to(String method, String uri) throws Throwable {
    this.world.requestUri = uri;
    String urlString = String.format("http://localhost:%d%s", PORT, uri);
    URL url = new URL(urlString);
    this.world.con = (HttpURLConnection) url.openConnection();
    this.world.con.setRequestMethod(method);
    this.world.con.setDoOutput(true);
  }

  @When("^the request contains the application/json message body$")
  public void the_request_contains_the_application_json_message_body() throws Throwable {
    this.world.con.setRequestProperty("Content-Type", "application/json");

    String json = "{ \"sampleKey\": \"sampleValue\", \"anotherSampleKey\": \"anotherSampleValue\" }";
    writeString(this.world.con, json);
  }

  @When("^the request contains the (.+) message body \"([^\"]*)\"$")
  public void the_request_contains_the_text_plain_message_body(String contentType, String body) throws Throwable {
    this.world.con.setRequestProperty("Content-Type", contentType);
    writeString(this.world.con, body);
  }

  private void writeString(HttpURLConnection con, String str) throws IOException {
    OutputStream out = con.getOutputStream();
    out.write(str.getBytes("UTF-8"));
    out.close();
  }
  
}
