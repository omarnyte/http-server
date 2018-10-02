package com.omarnyte.mock;

import com.omarnyte.jsonpatch.JsonPatchOperation;

public class MockJsonPatchOperation extends JsonPatchOperation {
  private String contentToReturn;

  public MockJsonPatchOperation(String contentToReturn) {
    super("mockOp", "mockPath");
    this.contentToReturn = contentToReturn;
  }
  
  @Override 
  public String applyOperation(String original) {
    return contentToReturn;
  }
  
}

