import java.util.Arrays;
import org.json.JSONObject;

public class JsonPatchReplace extends JsonPatchOperation {
  private String value;
  
  public JsonPatchReplace(String op, String path, String value) {
    super(op, path);
    this.value = value;
  }

  @Override 
  public String applyOperation(String original) throws BadRequestException, UnprocessableEntityException {
    JSONObject originalAsJson = new JSONObject(original);
    String[] keys = extractKeys(this.path);
    originalAsJson = replaceValue(originalAsJson, keys);
    return originalAsJson.toString();
  }

  private JSONObject replaceValue(JSONObject jsonObject, String[] keys) throws UnprocessableEntityException {
    String currentKey = keys[0];
    
    if (keys.length == 1 && jsonObject.has(currentKey)) {
      return jsonObject.put(currentKey, this.value);
    } else if (!jsonObject.has(currentKey)) {
      throw new UnprocessableEntityException(currentKey + "is not a valid key.");
    }

    return jsonObject.put(currentKey, getUpdatedNestedValue(jsonObject, keys));
  }

  private JSONObject getUpdatedNestedValue(JSONObject jsonObject, String[] keys) throws UnprocessableEntityException {
    String currentKey = keys[0];
    JSONObject nestedJsonObjectVal = jsonObject.getJSONObject(currentKey);
    String[] remainingKeys = Arrays.copyOfRange(keys, 1, keys.length);
    return replaceValue(nestedJsonObjectVal, remainingKeys);
  }

}
