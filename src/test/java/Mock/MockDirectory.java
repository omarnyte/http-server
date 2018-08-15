import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockDirectory extends Directory {
  private final static String DEFAULT_DIRECTORY_PATH = System.getProperty("TEST_DIRECTORY_PATH");
  
  private List<String> subdirectories;
  private List<String> files;   
  private Map<String, String> fileContents;
  private Map<String, String> fileTypes;
  
  public MockDirectory(List<String> subdirectories, List<String> files, Map fileContents, Map fileTypes) throws NonexistentDirectoryException {
    super(DEFAULT_DIRECTORY_PATH);
    this.subdirectories = subdirectories;
    this.files = files;
    this.fileContents = fileContents;
    this.fileTypes = fileTypes;
  }
  
  public String[] listContent() {
    String[] content = { "content" };
    return content; 
  }

  public Boolean existsInStore(String uri) {
    return this.subdirectories.contains(uri) || this.files.contains(uri);
  }

  public byte[] readFile(String uri) {
    String content = fileContents.get(uri);
    return content.getBytes();
  }

  public String getFileType(String uri) {
    return this.fileTypes.get(uri);
  }

  public void postFile (String uri, byte[] content) {
    this.files.add(uri);
  }

  public boolean deleteFile(String uri) {
    return this.files.remove(uri);
  }

}