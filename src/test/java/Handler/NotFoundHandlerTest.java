import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class NotFoundHandlerTest {
  private Request badRequest;

  @Before 
  public void setup() {
    this.badRequest = new Request.Builder()
                                 .method("GET")
                                 .uri("/this/path/does/not/exist")
                                 .version("1.1")
                                 .build();
  }
  
  @Test 
  public void returns404StatusCode() {
    NotFoundHandler handler = new NotFoundHandler();
    Response response = handler.generateResponse(this.badRequest);

    assertEquals(404, response.getStatusCode());
  }
  
}
