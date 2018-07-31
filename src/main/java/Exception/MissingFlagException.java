public class MissingFlagException extends Exception {

  public MissingFlagException (String flagType) {
    super(String.format("No flag was provided for %s.", flagType));
  }
}
