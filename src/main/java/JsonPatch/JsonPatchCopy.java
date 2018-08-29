import java.util.Arrays;
import org.json.JSONObject;

public class JsonPatchCopy extends JsonPatchOperation {
  private String from;

  public JsonPatchCopy(String op, String from, String path) {
    super(op, path);
    this.from = from;    
  }

  @Override 
  public String applyOperation(String original) throws BadRequestException, UnprocessableEntityException {
    JSONObject originalAsJson = new JSONObject(original);
    String[] fromKeys = extractKeys(this.from);
    String[] pathKeys = extractKeys(this.path);
    originalAsJson = copyValue(originalAsJson, fromKeys, pathKeys);
    return originalAsJson.toString();
  }
  
  private JSONObject copyValue(JSONObject jsonObject, String[] fromKeys, String[] pathKeys) throws UnprocessableEntityException {
    String toBeRemoved = getValueFromJsonObjectGivenKeys(jsonObject, fromKeys);
    jsonObject = addValue(toBeRemoved, jsonObject, pathKeys);
    return jsonObject;
  }
  
}
