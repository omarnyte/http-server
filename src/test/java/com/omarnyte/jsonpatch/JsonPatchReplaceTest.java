package com.omarnyte.jsonpatch;

import static org.junit.Assert.assertEquals; 
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test; 

import com.omarnyte.exception.BadRequestException;
import com.omarnyte.exception.UnprocessableEntityException;

public class JsonPatchReplaceTest {
  private final static String FIRST_KEY = "\"firstKey\"";
  private final static String FIRST_VALUE = "\"firstValue\"";
  private final static String OP = "replace";
  private final static String ORIGINAL_JSON = crateJsonBodyStringWithNewLines();
  private final static String REPLACE_VALUE = "willBeReplacingFirstKey";

  @Rule
  public ExpectedException thrown = ExpectedException.none();  

  @Test 
  public void throwsUnprocessableEntityExceptionIfTargetLocationDoesNotExist() throws BadRequestException, UnprocessableEntityException {
    String path = "/target/that/does/not/exist";
    JsonPatchOperation jsonPatch = new JsonPatchReplace(OP, path, FIRST_VALUE);

    thrown.expect(UnprocessableEntityException.class);
    jsonPatch.applyOperation(ORIGINAL_JSON);
  }
  
  @Test 
  public void appliesReplaceOperation() throws BadRequestException, UnprocessableEntityException {
    String path = "/firstKey";
    JsonPatchOperation operation = new JsonPatchReplace(OP, path, REPLACE_VALUE);
    
    String expectedJson = "{" + FIRST_KEY + ":" + "\"" + REPLACE_VALUE + "\""  + "}";
    assertEquals(expectedJson, operation.applyOperation(ORIGINAL_JSON));
  }

  private static String crateJsonBodyStringWithNewLines() {
    return "{\n" +
              FIRST_KEY + ": " + FIRST_VALUE + ",\n" +
            "}";
  }
  
}
