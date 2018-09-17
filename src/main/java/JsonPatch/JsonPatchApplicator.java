import java.util.ArrayList;

public class JsonPatchApplicator extends PatchApplicator {
  private static final String RESOURCE_CONTENT_TYPE = MimeType.JSON;
  
  private JsonPatchParser jsonPatchParser;
  
  public JsonPatchApplicator(JsonPatchParser jsonPatchParser) {
    this.jsonPatchParser = jsonPatchParser;
  }
  
  public String applyPatch(Request request, String resourceContent) throws BadRequestException, UnprocessableEntityException, UnsupportedMediaTypeException {
    String requestContentType = request.getHeader(MessageHeader.CONTENT_TYPE);
    if (!requestContentType.equals(MimeType.JSON_PATCH)) {
      throw new UnsupportedMediaTypeException(requestContentType, RESOURCE_CONTENT_TYPE);
    }

    return updateResourceWithPatchDocument(resourceContent, request.getBody());
  }

  private String updateResourceWithPatchDocument(String resourceContent, String patchDocument) throws BadRequestException, UnprocessableEntityException {
    ArrayList<JsonPatchOperation> operations = this.jsonPatchParser.getOperations(patchDocument);
    
    return applyOperationsToResource(operations, resourceContent);
  }

  private String applyOperationsToResource(ArrayList<JsonPatchOperation> operations, String resourceContent) throws BadRequestException, UnprocessableEntityException {
    String updatedResourceContent = resourceContent;
    for (JsonPatchOperation operation : operations) {
      updatedResourceContent = operation.applyOperation(updatedResourceContent);
    }
    
    return updatedResourceContent;
  }
  
}
