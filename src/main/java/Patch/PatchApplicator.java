public abstract class PatchApplicator {
  
  public abstract String applyPatch(Request request, String resouceContent) throws BadRequestException, UnprocessableEntityException, UnsupportedMediaTypeException;
  
}
