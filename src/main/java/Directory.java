import java.io.BufferedReader; 
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader; 
import java.io.IOException;
import java.util.Map;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class Directory {
  private static final String DEFAULT_FILE_TYPE = "application/octet-stream";
  private static final Map<String, String> MIME_TYPES = Map.ofEntries(
    Map.entry("gif", "image/gif"),
    Map.entry("html", "text/html"),
    Map.entry("jpg", "image/jpeg"),
    Map.entry("jpeg", "image/jpeg"),
    Map.entry("png", "image/png"),
    Map.entry("txt", "text/plain")
  );
  
  private File directory;
  private String directoryPath;

  public Directory(String directoryPath) throws NonexistentDirectoryException {
    this.directory = new File(directoryPath);
    if (this.directory.exists()) {
      this.directoryPath = directoryPath; 
    } else {
      throw new NonexistentDirectoryException(directoryPath);
    }
  }

  public Boolean isDirectory(String uri) {
    File file = new File(this.directoryPath + uri);
    return file.isDirectory();
  }

  public Boolean isFile(String uri) {
    File file = new File(this.directoryPath + uri);
    return file.isFile();
  }

  public String[] listContent() {
    return this.directory.list();
  }

  public Boolean existsInStore(String uri) {
    String filePath = this.directoryPath + uri;
    return new File(filePath).exists();
  }
  
  public byte[] readFile(String uri) {
    Path filePath = Paths.get(this.directoryPath + uri);
    byte[] fileBytes = null;
    try {
      fileBytes = Files.readAllBytes(filePath);
    } catch (IOException e) {
      System.err.println("Could not read file: " + uri);
      e.printStackTrace();
    }
    return fileBytes;
  }

  public String getFileType(String uri) {
    String filePath = this.directoryPath + uri;
    String extension = getExtension(filePath);
    return MIME_TYPES.getOrDefault(extension, DEFAULT_FILE_TYPE);
  }
  
  public boolean createFileWithContent(String uri, byte[] content) {
    try {
      File file = new File(this.directoryPath + uri);
      file.createNewFile();
      FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
      outputStream.write(content);
      return file.exists();
    } catch(IOException e) {
      System.err.println("IOException failed inside Directory");
      System.err.println(e);
      return false;
    }
  }

  public Directory createSubdirectory(String uri) throws NonexistentDirectoryException {
    return new Directory(this.directoryPath + uri);
  }
  
  public boolean deleteFile(String uri) {
    File file = new File(this.directoryPath + uri);
    return file.delete();
  }
  
  private String getExtension(String filePath) {
    return filePath.split("\\.")[1];
  }

}
