import java.util.ArrayList;
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test; 

public class JsonPatchParserTest {
  private final static String JSON_PATCH_BODY = createJsonPatchBodyWithTwoPatches();

  private static JsonPatchParser parser;
  
  @BeforeClass
  public static void setUp () {
    parser = new JsonPatchParser();
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();  

  @Test 
  public void throwsBadRequestExceptionWhenNotGivenAnArrayOfOperations() throws BadRequestException {
    String body = "{ \"op\": \"add\", \"path\": \"/a/b\" }";

    thrown.expect(BadRequestException.class);
    ArrayList<JsonPatchOperation> jsonPatches = parser.getOperations(body);
  }

  @Test 
  public void throwsBadRequestExceptionWithMissingOp() throws BadRequestException {
    String body = createJsonPatchBodyMissingOp();

    thrown.expect(BadRequestException.class);
    ArrayList<JsonPatchOperation> jsonPatches = parser.getOperations(body);
  }

  @Test 
  public void throwsBadRequestExceptionWithInvalidOp() throws BadRequestException {
    String body = createJsonPatchBodyWithInvalidOp();

    thrown.expect(BadRequestException.class);
    ArrayList<JsonPatchOperation> jsonPatches = parser.getOperations(body);
  }

  @Test 
  public void throwsBadRequestExceptionWithMissingPath() throws BadRequestException {
    String body = createJsonPatchBodyMissingPath();

    thrown.expect(BadRequestException.class);
    ArrayList<JsonPatchOperation> jsonPatches = parser.getOperations(body);
  }

  @Test 
  public void parsesSinglePatch() throws BadRequestException {
    String body = createJsonPatchBodyWithOnePatch();
    ArrayList<JsonPatchOperation> jsonPatches = parser.getOperations(body);
    assertEquals(1, jsonPatches.size());
  }

  @Test 
  public void parsesMultiplePatches() throws BadRequestException {
    String body = createJsonPatchBodyWithTwoPatches();
    ArrayList<JsonPatchOperation> jsonPatches = parser.getOperations(body);
    assertEquals(2, jsonPatches.size());
  }
  
  private static String createJsonPatchBodyMissingOp() {
    return 
    "[{ \"notOp\": \"remove\", \"path\": \"/a/b\" }]";
  }

  private static String createJsonPatchBodyWithInvalidOp() {
    return 
    "[{ \"op\": \"invalidOp\", \"path\": \"/a/b\" }]";
  }

  private static String createJsonPatchBodyMissingPath() {
    return 
    "[{ \"notOp\": \"remove\", \"path\": \"/a/b\" }]";
  }

  private static String createJsonPatchBodyWithOnePatch() {
    return 
    "[{ \"op\": \"remove\", \"path\": \"/a/b\" }]";
  }

  private static String createJsonPatchBodyWithTwoPatches() {
    return 
    "[{ \"op\": \"remove\", \"path\": \"/a/b\" }," +
     "{ \"op\": \"add\", \"path\": \"/a/b\", \"value\": \"bar\" }]";
  }

}
