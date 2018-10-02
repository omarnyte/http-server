package com.omarnyte.jsonpatch;

import org.json.JSONObject;

import com.omarnyte.exception.BadRequestException;
import com.omarnyte.exception.UnprocessableEntityException;

public class JsonPatchRemove extends JsonPatchOperation {

  public JsonPatchRemove(String op, String path) {
    super(op, path);
  }
  
  @Override 
  public String applyOperation(String original) throws BadRequestException, UnprocessableEntityException {
    JSONObject originalAsJson = new JSONObject(original);
    String[] keys = extractKeys(this.path);
    originalAsJson = removeValue(originalAsJson, keys);
    return originalAsJson.toString();
  }

}