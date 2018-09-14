import java.util.Arrays;
import java.io.InputStream;

public class MockInputStream extends InputStream {
  byte[] inputBytes;

  public MockInputStream(byte[] inputBytes) {
    this.inputBytes = inputBytes;
  }
  
  public int read() {
    int arrayLength = this.inputBytes.length;
    if (arrayLength == 0) return -1;

    byte firstByte = this.inputBytes[0];
    this.inputBytes = Arrays.copyOfRange(this.inputBytes, 1, arrayLength);
    return firstByte;
  }
  
}
