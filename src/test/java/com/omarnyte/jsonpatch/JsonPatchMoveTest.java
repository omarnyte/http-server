package com.omarnyte.jsonpatch;

import static org.junit.Assert.assertEquals; 
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test; 

import com.omarnyte.exception.BadRequestException;
import com.omarnyte.exception.UnprocessableEntityException;

public class JsonPatchMoveTest {
  private final static String FIRST_KEY = "\"firstKey\"";
  private final static String FIRST_VALUE = "\"firstValue\"";
  private final static String OP = "move";
  private final static String ORIGINAL_JSON = crateJsonBodyStringWithNewLines();
  private final static String WILL_BE_MOVED_KEY = "willBeMovedKey";
  private final static String WILL_BE_MOVED_VALUE = "\"willBeMovedValue\"";

  @Rule
  public ExpectedException thrown = ExpectedException.none();  

  @Test 
  public void throwsUnprocessableEntityExceptionIfTargetLocationDoesNotExist() throws BadRequestException, UnprocessableEntityException {
    String from = "/target/that/does/not/exist";
    String path = "/firstKey";
    JsonPatchOperation jsonPatch = new JsonPatchMove(OP, from, path);

    thrown.expect(UnprocessableEntityException.class);
    jsonPatch.applyOperation(ORIGINAL_JSON);
  }
    
  @Test 
  public void appliesMoveOperation() throws BadRequestException, UnprocessableEntityException {
    String from = "/willBeMovedKey";
    String path = "/firstKey";
    JsonPatchOperation operation = new JsonPatchMove(OP, from, path);
    
    String expectedJson = "{" +
                              FIRST_KEY + ":" + WILL_BE_MOVED_VALUE  +
                            "}";
    assertEquals(expectedJson, operation.applyOperation(ORIGINAL_JSON));
  }

  private static String crateJsonBodyStringWithNewLines() {
    return "{\n" +
              FIRST_KEY + ": " + FIRST_VALUE + ",\n" +
              WILL_BE_MOVED_KEY + ": " + WILL_BE_MOVED_VALUE + "\n" +
            "}";
  }
  
}
