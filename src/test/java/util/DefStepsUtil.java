import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;

public class DefStepsUtil {

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

}