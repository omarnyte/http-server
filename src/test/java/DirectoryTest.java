import java.io.File; 
import java.io.IOException; 
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test; 

public class DirectoryTest {
  private String currentDirectory = System.getProperty("user.dir");

  @Rule
  public ExpectedException thrown = ExpectedException.none();  

  @Test 
  public void throwsNonexistentDirectoryExceptionWithInvalidPath() throws NonexistentDirectoryException {
    String nonexistentPath = "path/that/does/not/exist";

    thrown.expect(NonexistentDirectoryException.class);
    Directory directory = new Directory(nonexistentPath);
  }
  
  @Test
  public void returnsTrueWhenFileExists() throws NonexistentDirectoryException {
    String testDirectoryPath = currentDirectory + "/src/test/resources/testFiles";

    Directory directory = new Directory(testDirectoryPath);
    String uri = "/around-the-world.txt";
    assertEquals(true, directory.existsInStore(uri));
  }

  @Test
  public void returnsFalseWhenFileExists() throws NonexistentDirectoryException {
    String testDirectoryPath = currentDirectory + "/src/test/resources/testFiles";

    Directory directory = new Directory(testDirectoryPath);
    String uri = "/does-not-exist.txt";
    assertEquals(false, directory.existsInStore(uri));
  }

  @Test
  public void readsFileContent() throws NonexistentDirectoryException {
    String testDirectoryPath = currentDirectory + "/src/test/resources/testFiles";

    Directory directory = new Directory(testDirectoryPath);
    String uri = "/sample-text.txt";
    assertEquals("This is a sample text file.\n", directory.read(uri));
  }

}