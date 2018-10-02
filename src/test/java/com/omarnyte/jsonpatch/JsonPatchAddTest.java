package com.omarnyte.jsonpatch;

import static org.junit.Assert.assertEquals; 
import org.junit.Test; 

import com.omarnyte.exception.BadRequestException;
import com.omarnyte.exception.UnprocessableEntityException;

public class JsonPatchAddTest {

  @Test 
  public void addsNewMemberIfTargetLocationDoesNotExist() throws BadRequestException, UnprocessableEntityException {
    String original = "{ \"foo\": \"bar\"}";
    JsonPatchAdd operation = new JsonPatchAdd("add", "/baz", "qux");
    
    String expectedString = "{" +
                              "\"foo\":\"bar\"," +
                              "\"baz\":\"qux\"" +
                            "}";
    String actualString = operation.applyOperation(original);
    assertEquals(expectedString, actualString);
  }

  @Test 
  public void replacesMemberIfTargetLocationExists() throws BadRequestException, UnprocessableEntityException {
    String original = "{ \"foo\": \"bar\"}";
    JsonPatchAdd operation = new JsonPatchAdd("add", "/foo", "buzz");
    
    String expectedString = "{" +
                              "\"foo\":\"buzz\"" +
                            "}";
    String actualString = operation.applyOperation(original);
    assertEquals(expectedString, actualString);
  }

}
