import java.io.BufferedReader; 
import java.io.File;
import java.io.FileReader; 
import java.io.IOException; 

public class Directory implements DataStore {
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
}
