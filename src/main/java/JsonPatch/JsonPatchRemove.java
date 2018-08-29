import java.util.Arrays;
import org.json.JSONObject;

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