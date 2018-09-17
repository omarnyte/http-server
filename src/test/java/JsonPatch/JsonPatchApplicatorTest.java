import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test;

public class JsonPatchApplicatorTest {
  private final static String FIRST_JSON_PATCH_OPERATION_RESULT = "Updated by first MockJsonPatchOperation";
  private final static String ORIGINAL_RESOURCE_CONTENT = "Original resource content";
  private final static String SECOND_JSON_PATCH_OPERATION_RESULT = "Updated by second MockJsonPatchOperation";
  
  private static Request jsonPatchRequest;

  @BeforeClass 
  public static void setUpOnce() {
    jsonPatchRequest = new Request.Builder()
                    .setHeader(MessageHeader.CONTENT_TYPE, MimeType.JSON_PATCH)
                    .build();
  }
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();  

  @Test 
  public void throwsUnsupportedMediaExceptionWhenNotGivenJsonPatchMediaType() throws BadRequestException, UnprocessableEntityException, UnsupportedMediaTypeException {
    JsonPatchApplicator applicator = setUpApplicatorWithOneOperation();
    Request request = buildNonJsonPatchRequest();

    thrown.expect(UnsupportedMediaTypeException.class);
    applicator.applyPatch(request, ORIGINAL_RESOURCE_CONTENT);
  }

  @Test 
  public void appliesSinglePatchOperationToResourceContent() throws BadRequestException, UnprocessableEntityException, UnsupportedMediaTypeException {
    JsonPatchApplicator applicator = setUpApplicatorWithOneOperation();
                
    String updatedResource = applicator.applyPatch(jsonPatchRequest, ORIGINAL_RESOURCE_CONTENT);
    assertEquals(FIRST_JSON_PATCH_OPERATION_RESULT, updatedResource);
  }

  @Test 
  public void appliesMultiplePatchOperationsToResourceContent() throws BadRequestException, UnprocessableEntityException, UnsupportedMediaTypeException {
    JsonPatchApplicator applicator = setUpApplicatorWithMultipleOperations();
                
    String updatedResource = applicator.applyPatch(jsonPatchRequest, ORIGINAL_RESOURCE_CONTENT);
    assertEquals(SECOND_JSON_PATCH_OPERATION_RESULT, updatedResource);
  }

  private Request buildNonJsonPatchRequest() {
    String nonJsonPatchContentType = "not/json-patch";
    return new Request.Builder()
                .setHeader(MessageHeader.CONTENT_TYPE, nonJsonPatchContentType)
                .build();
  }

  private JsonPatchApplicator setUpApplicatorWithOneOperation() {
    ArrayList<JsonPatchOperation> mockOperations = setUpSingleMockOperation();
    JsonPatchParser mockJsonPatchParser = new MockJsonPatchParser(mockOperations);
    return new JsonPatchApplicator(mockJsonPatchParser);
  }

  private JsonPatchApplicator setUpApplicatorWithMultipleOperations() {
    ArrayList<JsonPatchOperation> mockOperations = setUpMultipleMockOperations();
    JsonPatchParser mockJsonPatchParser = new MockJsonPatchParser(mockOperations);
    return new JsonPatchApplicator(mockJsonPatchParser);
  }

  private JsonPatchParser setUpMockJsonPatchParser() {
    ArrayList<JsonPatchOperation> mockOperations = setUpMockJsonPatchOperations();
    return new MockJsonPatchParser(mockOperations);
  }

  private ArrayList<JsonPatchOperation> setUpMockJsonPatchOperations() {
    ArrayList<JsonPatchOperation> operations = new ArrayList<JsonPatchOperation>();
    operations.add(new MockJsonPatchOperation(FIRST_JSON_PATCH_OPERATION_RESULT));
    return operations;
  }  

  private ArrayList<JsonPatchOperation> setUpSingleMockOperation() {
    ArrayList<JsonPatchOperation> operations = new ArrayList<JsonPatchOperation>();
    String updatedContent = FIRST_JSON_PATCH_OPERATION_RESULT;
    operations.add(new MockJsonPatchOperation(FIRST_JSON_PATCH_OPERATION_RESULT));
    return operations;
  }   

  private ArrayList<JsonPatchOperation> setUpMultipleMockOperations() {
    ArrayList<JsonPatchOperation> operations = new ArrayList<JsonPatchOperation>();
    String updatedContent = FIRST_JSON_PATCH_OPERATION_RESULT;
    operations.add(new MockJsonPatchOperation(FIRST_JSON_PATCH_OPERATION_RESULT));
    operations.add(new MockJsonPatchOperation(SECOND_JSON_PATCH_OPERATION_RESULT));
    return operations;
  }   

}
