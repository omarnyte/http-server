package com.omarnyte.jsonpatch;

import org.json.JSONObject;

import com.omarnyte.exception.BadRequestException;
import com.omarnyte.exception.UnprocessableEntityException;

public class JsonPatchAdd extends JsonPatchOperation {
  private String value;
  
  public JsonPatchAdd(String op, String path, String value) {
    super(op, path);
    this.value = value;
  }

  @Override 
  public String applyOperation(String original) throws BadRequestException, UnprocessableEntityException {
    JSONObject originalAsJson = new JSONObject(original);
    String[] keys = extractKeys(this.path);
    originalAsJson = addValue(this.value, originalAsJson, keys);
    return originalAsJson.toString();
  }

}
