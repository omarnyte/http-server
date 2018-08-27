import java.util.Random;

public class Util {

  public static String createRandomFileName(String extension) {
    int rand = new Random().nextInt(999999999) + 100000000;
    return Integer.toString(rand) + extension;
  }

}