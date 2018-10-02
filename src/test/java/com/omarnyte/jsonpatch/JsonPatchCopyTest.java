package com.omarnyte.jsonpatch;

import static org.junit.Assert.assertEquals; 
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test;
 
import com.omarnyte.exception.BadRequestException;
import com.omarnyte.exception.UnprocessableEntityException;

public class JsonPatchCopyTest {
  private final static String FIRST_KEY = "\"firstKey\"";
  private final static String FIRST_VALUE = "\"firstValue\"";
  private final static String OP = "move";
  private final static String ORIGINAL_JSON = crateJsonBodyStringWithNewLines();
  private final static String NEW_KEY = "\"newKey\"";
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();  

  @Test 
  public void throwsUnprocessableEntityExceptionIfFromLocationDoesNotExist() throws BadRequestException, UnprocessableEntityException {
    String from = "/target/that/does/not/exist";
    String path = "/firstKey";
    JsonPatchOperation jsonPatch = new JsonPatchCopy(OP, from, path);

    thrown.expect(UnprocessableEntityException.class);
    jsonPatch.applyOperation(ORIGINAL_JSON);
  }

  @Test 
  public void appliesCopyOperation() throws BadRequestException, UnprocessableEntityException {
    String from = "/firstKey";
    String path = "/newKey";
    JsonPatchOperation operation = new JsonPatchCopy(OP, from, path);
    
    String expectedJson = "{" +
                              NEW_KEY + ":" + FIRST_VALUE + "," + 
                              FIRST_KEY + ":" + FIRST_VALUE  + 
                            "}";
    assertEquals(expectedJson, operation.applyOperation(ORIGINAL_JSON));
  }

  private static String crateJsonBodyStringWithNewLines() {
    return "{\n" +
              FIRST_KEY + ": " + FIRST_VALUE + "\n" + 
            "}";
  }
    
}