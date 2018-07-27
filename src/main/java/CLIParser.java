import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CLIParser {
  private static final String DIRECTORY_FLAG = "-dir";
  private static final String PORT_FLAG = "-port";
  private static final List<String> POSSIBLE_STORES = Arrays.asList( DIRECTORY_FLAG );
  private static final List<String> VALID_FLAGS = Arrays.asList( DIRECTORY_FLAG, PORT_FLAG );

  private String[] args;
  private HashMap<String, String> flagsMap;

  public CLIParser(String[] args) {
    this.args = args;
    try {
      this.flagsMap = createFlagsMap();
    } catch (UnsupportedFlagException e) {
      System.err.println(e.getMessage());
    }
  }

  public Boolean containsDirectoryFlag() {
    return Arrays.asList(args).contains(DIRECTORY_FLAG);
  }

  public int getPortNumberOrDefault(int defaultPortNumber) {
    String portAsString = this.flagsMap.get(PORT_FLAG);
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
    return this.flagsMap.get(DIRECTORY_FLAG);
  }

  private HashMap<String, String> createFlagsMap() throws UnsupportedFlagException {
    HashMap<String, String> map = new HashMap<String, String>();
    
    for (int i = 0; i < this.args.length - 1; i += 2) {
      String flag = this.args[i];
      if (!VALID_FLAGS.contains(flag)) {
        throw new UnsupportedFlagException(flag);
      }

      String arg = this.args[i + 1];
      map.put(flag, arg);
    }

    return map; 
  }

}
