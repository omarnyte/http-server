package com.omarnyte.mock;

import java.util.ArrayList;

import com.omarnyte.exception.BadRequestException;
import com.omarnyte.jsonpatch.JsonPatchOperation;
import com.omarnyte.jsonpatch.JsonPatchParser;

public class MockJsonPatchParser extends JsonPatchParser {
  private ArrayList<JsonPatchOperation>  mockOperations;

  public MockJsonPatchParser(ArrayList<JsonPatchOperation>  mockOperations) {
    this.mockOperations = mockOperations;
  }
  
  public ArrayList<JsonPatchOperation> getOperations(String body) throws BadRequestException {    
    return this.mockOperations;
  } 

}
