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

  // must not match PATCH request becuase PATCH requires X-HTTP-Method-Override for HttpURLConnection to make request
  @When("^a client makes a (?!PATCH)([A-Z]+) request to (.+)$")
  public void a_client_makes_a_request_to(String method, String uri) throws Throwable {
    this.world.requestUri = uri;
    String urlString = String.format("http://localhost:%d%s", PORT, uri);
    URL url = new URL(urlString);
    this.world.con = (HttpURLConnection) url.openConnection();
    this.world.con.setRequestMethod(method);
    this.world.con.setDoOutput(true);
  }

  @When("^a client makes a PATCH request to (.+)$")
  public void a_client_makes_a_PATCH_request_to(String uri) throws Throwable {
    this.world.requestUri = uri;
    String urlString = String.format("http://localhost:%d%s", PORT, uri);
    URL url = new URL(urlString);
    this.world.con = (HttpURLConnection) url.openConnection();
    this.world.con.setRequestProperty("X-HTTP-Method-Override", "PATCH");
    this.world.con.setDoOutput(true);
  }

  @When("^the request contains the (.+) message body$")
  public void the_request_contains_the_message_body_block(String contentType, String content) throws Throwable {
    this.world.con.setRequestProperty("Content-Type", contentType);
    writeString(this.world.con, content);
  }

  private void writeString(HttpURLConnection con, String str) throws IOException {
    OutputStream out = con.getOutputStream();
    out.write(str.getBytes("UTF-8"));
    out.close();
  }
  
}
