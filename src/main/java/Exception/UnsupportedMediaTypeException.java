public class UnsupportedMediaTypeException extends Exception {

  public UnsupportedMediaTypeException(String mediaType, String resourceType) {
    
      super(mediaType + " is not a supported Patch Document for " + resourceType);
  }

}
