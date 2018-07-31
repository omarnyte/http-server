import java.io.BufferedReader; 
import java.io.File;
import java.io.FileReader; 
import java.io.IOException;

import java.util.Map;

public class Directory implements DataStore {
  private static final String DEFAULT_FILE_TYPE = "application/octet-stream";
  private static final Map<String, String> MIME_TYPES = Map.ofEntries(
      Map.entry("html", "text/html"),
      Map.entry("txt", "text/plain")
  );
  
  private File directory;
  private String directoryPath;

  public Directory(String directoryPath) throws NonexistentDirectoryException {
    File directory = new File(directoryPath);
    if (directory.exists()) {
      this.directoryPath = directoryPath; 
    } else {
      throw new NonexistentDirectoryException(directoryPath);
    }
  }

  public String listContent() {
    String[] contentsOfDirectory = getContentsOfDirectory();
    return stringifyContentsOfDirectory(contentsOfDirectory);
  }

  public Boolean existsInStore(String uri) {
    String filePath = this.directoryPath + uri;
    return new File(filePath).exists();
  }
  
  public String read(String uri) {
    String filePath = this.directoryPath + uri;
    String content = ""; 
     
    try { 
      BufferedReader reader = new BufferedReader(new FileReader(filePath)); 
      String line; 
      while ((line = reader.readLine()) != null) { 
        content += line + "\n"; 
      } 
    } catch(IOException e) { 
      e.printStackTrace(); 
    } 
 
    return content; 
  }
  
  private String[] getContentsOfDirectory() {
    File directory = new File(this.directoryPath);
    return directory.list();
  }

  private String stringifyContentsOfDirectory(String[] fileNames) {
    String content = "";
    
    if (fileNames.length == 0) {
      return "Empty directory!";
    }
    
    for (String fileName : fileNames) {
      content += fileName + "\n";
    }
    
    return content;
  }

  public String getFileType(String uri) {
    String filePath = this.directoryPath + uri;
    String extension = getExtension(filePath);
    return MIME_TYPES.getOrDefault(extension, DEFAULT_FILE_TYPE);
  }

  private String getExtension(String filePath) {
    return filePath.split("\\.")[1];
  }
  
  
  private String[] getContentsOfDirectory() {
    File directory = new File(this.directoryPath);
    return directory.list();
  }

  private String stringifyContentsOfDirectory(String[] fileNames) {
    String content = "";
    
    if (fileNames.length == 0) {
      return "Empty directory!";
    }
    
    for (String fileName : fileNames) {
      content += fileName + "\n";
    }
    
    return content;
  }
}
