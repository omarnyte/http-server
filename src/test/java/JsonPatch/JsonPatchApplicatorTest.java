import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test;

public class JsonPatchApplicatorTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();  

  @Test 
  public void throwsUnsupportedMediaExceptionWhenNotGivenJsonPatchMediaType() throws UnsupportedMediaTypeException {
    Request request = new Request.Builder()
                      .setHeader(MessageHeader.CONTENT_TYPE, "not/json-patch")
                      .build();

    thrown.expect(UnsupportedMediaTypeException.class);
    JsonPatchApplicator jsonPatchApplicator = new JsonPatchApplicator(new JsonPatchParser());
    jsonPatchApplicator.applyPatch(request, "hello");
  }

  
}