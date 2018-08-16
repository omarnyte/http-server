import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException; 
import java.io.PrintWriter;

public class TempDirectory {
  private File directory;
  private String directoryPath; 
  
  public TempDirectory(String directoryPath) {
    this.directoryPath = directoryPath;
    this.directory = new File(directoryPath);
    this.directory.mkdir();
    this.directory.deleteOnExit();
  }

  public void createEmptyFile(String fileName) throws IOException {
    File tempFile = new File(this.directoryPath + "/" + fileName);
    tempFile.createNewFile(); 
    tempFile.deleteOnExit();
  }

  public void createFileWithContent(String fileName, String content) throws IOException {
    createEmptyFile(fileName);

    String tempFilePath = this.directoryPath + "/" + fileName;
    try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(tempFilePath)))) {
      out.print(content);
    } catch (IOException e) {
      System.err.println("Could not write to: " + tempFilePath);
    }
  }

  public static void deleteSubdirectories(File directory) {
    for (File sub : directory.listFiles()) {
      if (sub.isDirectory()) {
        TempDirectory.deleteSubdirectories(sub);
      } else {
        sub.delete();
      }
    }
    directory.delete();
  }
  
}
