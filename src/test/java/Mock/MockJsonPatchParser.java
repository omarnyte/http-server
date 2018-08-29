import java.util.ArrayList;

public class MockJsonPatchParser extends JsonPatchParser {
  private ArrayList<JsonPatchOperation>  mockOperations;

  public MockJsonPatchParser(ArrayList<JsonPatchOperation>  mockOperations) {
    this.mockOperations = mockOperations;
  }
  
  public ArrayList<JsonPatchOperation> getOperations(String body) throws BadRequestException {    
    return this.mockOperations;
  } 

}
