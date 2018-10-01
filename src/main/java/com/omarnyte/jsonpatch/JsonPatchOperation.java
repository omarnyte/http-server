package com.omarnyte.jsonpatch;

import org.json.JSONObject;
import java.util.Arrays;

import com.omarnyte.exception.BadRequestException;
import com.omarnyte.exception.UnprocessableEntityException;

public abstract class JsonPatchOperation {
  protected String op; 
  protected String path;

  public JsonPatchOperation(String op, String path) {
    this.op = op;
    this.path = path;
  }

  public abstract String applyOperation(String original) throws BadRequestException, UnprocessableEntityException; 

  protected String[] extractKeys(String path) throws BadRequestException {
    String leadingSlash = "/";
    if (!path.startsWith(leadingSlash)) throw new BadRequestException("Path must begin with a leading '/'");
  
    return path.substring(1).split(leadingSlash);
  }

  protected JSONObject addValue(String value, JSONObject jsonObject, String[] keys) throws UnprocessableEntityException {
    String currentKey = keys[0];
    
    if (keys.length == 1) {
      return jsonObject.put(currentKey, value);
    } else if (!jsonObject.has(currentKey)) {
      throw new UnprocessableEntityException(currentKey + "is not a valid key.");
    }

    JSONObject nestedJsonObjectVal = jsonObject.getJSONObject(currentKey);
    String[] remainingKeys = Arrays.copyOfRange(keys, 1, keys.length);
    JSONObject updatedNestedValue = addValue(value, nestedJsonObjectVal, remainingKeys);
    return jsonObject.put(currentKey, updatedNestedValue);
  }

  protected JSONObject removeValue(JSONObject jsonObject, String[] keys) throws UnprocessableEntityException {
    String currentKey = keys[0];
    
    if (keys.length == 1 && jsonObject.has(currentKey)) {
      jsonObject.remove(currentKey);
      return jsonObject;
    } else if (!jsonObject.has(currentKey)) {
      throw new UnprocessableEntityException(currentKey + "is not a valid key.");
    }

    JSONObject nestedJsonObjectVal = jsonObject.getJSONObject(currentKey);
    String[] remainingKeys = Arrays.copyOfRange(keys, 1, keys.length);
    JSONObject updatedNestedValue = removeValue(nestedJsonObjectVal, remainingKeys);
    return jsonObject.put(currentKey, updatedNestedValue);
  }


  protected String getValueFromJsonObjectGivenKeys(JSONObject jsonObject, String[] keys) throws UnprocessableEntityException {
    String currentKey = keys[0];

    if (keys.length == 1 && jsonObject.has(currentKey)) {
      return jsonObject.getString(currentKey);
    } else if (!jsonObject.has(currentKey)) {
      throw new UnprocessableEntityException(currentKey + "is not a valid key.");
    }

    JSONObject nestedJsonObjectVal = jsonObject.getJSONObject(currentKey);
    String[] remainingKeys = Arrays.copyOfRange(keys, 1, keys.length);
    return getValueFromJsonObjectGivenKeys(nestedJsonObjectVal, remainingKeys);
  }

}
