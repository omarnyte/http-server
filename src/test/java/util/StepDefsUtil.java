import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path; 
import java.nio.file.Paths; 

public class StepDefsUtil {
  public static String readMessageBody(HttpURLConnection con) throws IOException {
    BufferedReader in = new BufferedReader(
      new InputStreamReader(con.getInputStream()));
    String inputLine;
    String messageBody = "";
    while ((inputLine = in.readLine()) != null) {
        messageBody += inputLine + "\n";
    }
    in.close();

    return messageBody;
  }

  public static byte[] readMessageBodyBytes(HttpURLConnection con) throws IOException {
    InputStream in = con.getInputStream();
    return in.readAllBytes();
  }

  public static byte[] readFile(String filePathString) {
    Path filePath = Paths.get(filePathString);
    byte[] fileBytes = null;
    try {
      fileBytes = Files.readAllBytes(filePath);
    } catch (IOException e) {
      System.err.println("Could not read file: " + filePathString);
      e.printStackTrace();
    }
    return fileBytes;
  }

}
