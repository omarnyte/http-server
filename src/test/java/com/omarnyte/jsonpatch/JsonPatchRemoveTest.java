package com.omarnyte.jsonpatch;

import static org.junit.Assert.assertEquals; 
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test; 

import com.omarnyte.exception.BadRequestException;
import com.omarnyte.exception.UnprocessableEntityException;

public class JsonPatchRemoveTest {
  private final static String FIRST_KEY = "\"firstKey\"";
  private final static String FIRST_VALUE = "\"firstValue\"";
  private final static String OP = "remove";
  private final static String ORIGINAL_JSON = crateJsonBodyStringWithNewLines();
  private final static String WILL_BE_REMOVED_KEY = "willBeRemovedKey";
  private final static String WILL_BE_REMOVED_VALUE = "\"willBeRemovedValue\"";
    
  @Rule
  public ExpectedException thrown = ExpectedException.none();  

  @Test 
  public void throwsUnprocessableEntityExceptionIfTargetLocationDoesNotExist() throws BadRequestException, UnprocessableEntityException {
    String path = "/target/that/does/not/exist";
    JsonPatchOperation jsonPatch = new JsonPatchRemove(OP, path);

    thrown.expect(UnprocessableEntityException.class);
    jsonPatch.applyOperation(ORIGINAL_JSON);
  }
  
  @Test 
  public void removesTheValueAtTargetLocation() throws BadRequestException, UnprocessableEntityException {
    String path = "/willBeRemovedKey";
    JsonPatchOperation jsonPatch = new JsonPatchRemove(OP, path);
    
    String expectedJson = "{" +
                              FIRST_KEY + ":" + FIRST_VALUE  +
                            "}";
    assertEquals(expectedJson, jsonPatch.applyOperation(ORIGINAL_JSON));
  }

  private static String crateJsonBodyStringWithNewLines() {
    return "{\n" +
              FIRST_KEY + ": " + FIRST_VALUE + ",\n" +
              WILL_BE_REMOVED_KEY + ": " + WILL_BE_REMOVED_VALUE + "\n" +
            "}";
  }

}
