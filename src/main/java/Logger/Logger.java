import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger extends Middleware {
  private String dateTimePattern;
  private String logDirectoryPath;
  private File logFile;



  LogFormatter logFormatter = new LogFormatter();




  public Logger(String logDirectoryPath, String dateTimePattern) throws LoggerException {
    this.logDirectoryPath = logDirectoryPath;
    this.dateTimePattern = dateTimePattern;
  }

  public Request applyMiddleware(Request request) {
    String requestFormattedForLogger = this.logFormatter.formatRequest(request);
    logEntry(requestFormattedForLogger);
    return checkNext(request);
  }

  public Response applyMiddleware(Response response) {
    String responseFormattedForLogger = this.logFormatter.formatResponse(response);
    logEntry(responseFormattedForLogger);
    return checkNext(response);
  }



  public File createLogFile() throws LoggerException {
    try {
      String logFileName = getFormattedTime() + ".txt";
      this.logFile = new File(logDirectoryPath + "/" + logFileName);
      logFile.createNewFile();
      return logFile;
    } catch (IOException e) {
      throw new LoggerException("Could not create log file in path: " + this.logDirectoryPath);
    }
  }
  
  public void logEntry(String entry) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(this.logFile, true))) {
      writer.println(entry);
    } catch(IOException e) {
      System.err.println("Could not log entry: " + entry);
    }
  }

  private String getFormattedTime() {
    DateFormat dateFormat = new SimpleDateFormat(this.dateTimePattern);
    return dateFormat.format(new Date());
  }

}
