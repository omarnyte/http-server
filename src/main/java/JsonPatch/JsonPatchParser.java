import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class JsonPatchParser {

  public ArrayList<JsonPatchOperation> getOperations(String body) throws BadRequestException {    
    try {
      ArrayList<JsonPatchOperation> operations = new ArrayList<JsonPatchOperation>();
      Iterator iterator  = new JSONArray(body).iterator();
      while (iterator.hasNext()) {
        JSONObject jsonPatchObject = (JSONObject)iterator.next();
        JsonPatchOperation operation = createPatchFromJsonObject(jsonPatchObject);
        operations.add(operation);
      }
      return operations;
    } catch (Exception e) {
      System.out.println(e);
      throw new BadRequestException("Could not parse application/json-patch+json");
    }
  }

  private JsonPatchOperation createPatchFromJsonObject(JSONObject jsonObject) throws BadRequestException {
    try {
      switch (jsonObject.getString("op")) { 
        case "add":  
          return createJsonPatchAdd(jsonObject); 
        case "remove":  
          return createJsonPatchRemove(jsonObject); 
        case "replace":  
          return createJsonPatchReplace(jsonObject); 
        case "move":  
          return createJsonPatchMove(jsonObject); 
        case "copy":  
          return createJsonPatchCopy(jsonObject); 
        default: 
          throw new BadRequestException(jsonObject.getString("op") + " is not a valid JSON PATCH operation.");
      }
    } catch (JSONException e) {
      System.out.println(e);
      throw new BadRequestException("Couldn't parse JSON Patch operation");
    }
  }

  private JsonPatchOperation createJsonPatchAdd(JSONObject jsonObject) {
    String op = jsonObject.getString("op");
    String path = jsonObject.getString("path");
    String value = jsonObject.getString("value");
    return new JsonPatchAdd(op, path, value);
  }

  private JsonPatchOperation createJsonPatchRemove(JSONObject jsonObject) {
    String op = jsonObject.getString("op");
    String path = jsonObject.getString("path");
    return new JsonPatchRemove(op, path);
  }

  private JsonPatchOperation createJsonPatchReplace(JSONObject jsonObject) {
    String op = jsonObject.getString("op");
    String path = jsonObject.getString("path");
    String value = jsonObject.getString("value");
    return new JsonPatchReplace(op, path, value);
  }
  
  private JsonPatchOperation createJsonPatchMove(JSONObject jsonObject) {
    String op = jsonObject.getString("op");
    String from = jsonObject.getString("from");
    String path = jsonObject.getString("path");
    return new JsonPatchMove(op, from, path);
  }

  private JsonPatchOperation createJsonPatchCopy(JSONObject jsonObject) {
    String op = jsonObject.getString("op");
    String from = jsonObject.getString("from");
    String path = jsonObject.getString("path");
    return new JsonPatchCopy(op, from, path);
  }

}
