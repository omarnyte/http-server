public class UnsupportedFlagException extends Exception {

  public UnsupportedFlagException (String flag) {
    super(String.format("%s is not a supported flag.", flag));
  }
}
