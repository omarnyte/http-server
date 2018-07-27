import java.util.Arrays;
import java.util.List;

public class CLIParser {
  private static final String DIRECTORY_FLAG = "-dir";
  private static final String PORT_FLAG = "-port";
  private static final List<String> POSSIBLE_STORES = Arrays.asList( DIRECTORY_FLAG );

  private String[] args;

  public CLIParser(String[] args) {
    this.args = args;
  }

  public Boolean containsDirectoryFlag() {
    return Arrays.asList(args).contains(DIRECTORY_FLAG);
  }

  public int getPortNumberOrDefault(int defaultPortNumber) {
    String portAsString = getArgumentByFlag(PORT_FLAG);
    return (portAsString == null) ? defaultPortNumber : Integer.parseInt(portAsString);
  }

  public String getStoreFlag() {
    String flag = null;
    
    for (int i = 0; i < args.length; i++) {
      if (POSSIBLE_STORES.contains(args[i])) {
        flag = args[i];
      } 
    }

    return flag;
  }
  
  public String getDirectory() {    
    return getArgumentByFlag(DIRECTORY_FLAG);
  }

  private String getArgumentByFlag(String flag) {    
    String arg = null;
    
    for (int i = 0; i < args.length; i++) {
      if (this.args[i].equals(flag)) {
        arg = args[i + 1];
      } 
    }

    return arg;
  }

}
