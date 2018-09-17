import java.util.ArrayList;

public class JsonPatchApplicator extends PatchApplicator {
  private static final String RESOURCE_CONTENT_TYPE = MimeType.JSON;
  
  private JsonPatchParser jsonPatchParser;
  
  public JsonPatchApplicator(JsonPatchParser jsonPatchParser) {
    this.jsonPatchParser = jsonPatchParser;
  }
  
  public String applyPatch(Request request, String resourceContent) throws UnsupportedMediaTypeException {
    String requestContentType = request.getHeader(MessageHeader.CONTENT_TYPE);
    if (!requestContentType.equals(MimeType.JSON_PATCH)) {
      throw new UnsupportedMediaTypeException(requestContentType, RESOURCE_CONTENT_TYPE);
    }

    return resourceContent;
  }
  
  // public String applyPatch(String patchDocument, String resourceContent) throws BadRequestException, UnprocessableEntityException {
  //   String updatedResourceContent = resourceContent;
  //   ArrayList<JsonPatchOperation> patches = this.jsonPatchParser.getOperations(patchDocument);
  //   for (JsonPatchOperation patch : patches) {
  //     updatedResourceContent = patch.applyOperation(updatedResourceContent);
  //   }

  //   return updatedResourceContent;
  // }
  
}
