public abstract class PatchApplicator {
  
  public static boolean isPatchable(String resouceContentType) {
    return resouceContentType.equals(MimeType.JSON);
  }
  
  public static PatchApplicator getPatchApplicator(String resourceContentType) {
    return new JsonPatchApplicator(new JsonPatchParser());
  }
  
  public abstract String applyPatch(Request request, String resouceContent) throws BadRequestException, UnprocessableEntityException, UnsupportedMediaTypeException;
  
}
