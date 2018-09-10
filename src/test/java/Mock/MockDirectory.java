import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockDirectory extends Directory {
  private final static String DEFAULT_DIRECTORY_PATH = System.getProperty("TEST_DIRECTORY_PATH");
  
  private List<String> subdirectories = new ArrayList<String>();
  private List<String> files = new ArrayList<String>();
  private HashMap<String, String> fileContents = new HashMap<String, String>();
  private Map<String, String> fileTypes = new HashMap<String, String>();
  
  public MockDirectory() throws NonexistentDirectoryException {
    super(DEFAULT_DIRECTORY_PATH);
  }
  
  public MockDirectory(List<String> files, HashMap fileContents, Map fileTypes) throws NonexistentDirectoryException {
    super(DEFAULT_DIRECTORY_PATH);
    this.files = files;
    this.fileContents = fileContents;
    this.fileTypes = fileTypes;
  }

  public MockDirectory(List<String> subdirectories, List<String> files, HashMap fileContents, Map fileTypes) throws NonexistentDirectoryException {
    super(DEFAULT_DIRECTORY_PATH);
    this.subdirectories = subdirectories;
    this.files = files;
    this.fileContents = fileContents;
    this.fileTypes = fileTypes;
  }

  public Boolean isDirectory(String uri) {
    return this.subdirectories.contains(uri);
  }

  public Boolean isFile(String uri) {
    return this.files.contains(uri);
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

  public boolean createFileWithContent(String uri, byte[] content) {
    this.files.add(uri);
    this.fileContents.put(uri, content.toString());
    return true;
  }

  public boolean overwriteFileWithContent(String uri, byte[] content) {
    this.fileContents.replace(uri, new String(content));
    return true;
  }

  public boolean overwriteFileWithStringContent(String uri, String content) {
    this.fileContents.replace(uri, content);
    return true;
  }

  public Directory createSubdirectory(String uri) throws NonexistentDirectoryException {
    return new MockDirectory();
  }
  
  public boolean deleteFile(String uri) {
    return this.files.remove(uri);
  }

}
