import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CLIParser {
  private static final String DIRECTORY_FLAG = "-dir";
  private static final String PORT_FLAG = "-port";
  private static final List<String> VALID_STORE_FLAGS = Arrays.asList( DIRECTORY_FLAG );
  private static final List<String> VALID_FLAGS = Arrays.asList( DIRECTORY_FLAG, PORT_FLAG );

  private String[] args;
  private HashMap<String, String> flagsMap;

  public CLIParser(String[] args) throws UnsupportedFlagException {
    this.args = args;
    this.flagsMap = createFlagsMap();
  }

  public int getPortNumberOrDefault(int defaultPortNumber) {
    String portAsString = this.flagsMap.get(PORT_FLAG);
    return (portAsString == null) ? defaultPortNumber : Integer.parseInt(portAsString);
  }

  public String getStoreFlag() throws MissingFlagException {
    Optional<String> foundFlag = VALID_STORE_FLAGS.stream()
                                                  .filter(flag -> this.flagsMap.containsKey(flag))
                                                  .findFirst();
    if (foundFlag.isPresent()) {
      return foundFlag.get(); 
    } else {
      throw new MissingFlagException("store");
    }
  }

  public String getDirectory() {    
    return this.flagsMap.get(DIRECTORY_FLAG);
  }

  public String printValidFlags() {
    return String.join(", ", this.VALID_FLAGS);
  }

  public String printValidStoreFlags() {
    return String.join(", ", this.VALID_STORE_FLAGS);
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
