import java.io.File; 
import java.io.IOException; 
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test; 

public class DirectoryTest {
  private String testDirectoryPath = System.getProperty("user.dir") + "/src/test/resources/testFiles";

  @Rule
  public ExpectedException thrown = ExpectedException.none();  

  @Test 
  public void throwsNonexistentDirectoryExceptionWithInvalidPath() throws NonexistentDirectoryException {
    String nonexistentPath = "path/that/does/not/exist";

    thrown.expect(NonexistentDirectoryException.class);
    Directory directory = new Directory(nonexistentPath);
  }

  @Test 
  public void listsDirectoryContent() throws NonexistentDirectoryException {
    Directory directory = new Directory(this.testDirectoryPath);
    assertEquals("around-the-world.txt\n" +
                 "fresh-prince-of-bel-air.txt\n" + 
                 "sample-text.txt\n", directory.listContent());
  }
  
  @Test
  public void returnsTrueWhenFileExists() throws NonexistentDirectoryException {
    Directory directory = new Directory(this.testDirectoryPath);
    String uri = "/around-the-world.txt";
    assertEquals(true, directory.existsInStore(uri));
  }

  @Test
  public void returnsFalseWhenFileExists() throws NonexistentDirectoryException {
    Directory directory = new Directory(this.testDirectoryPath);
    String uri = "/does-not-exist.txt";
    assertEquals(false, directory.existsInStore(uri));
  }

  @Test
  public void readsFileContent() throws NonexistentDirectoryException {
    Directory directory = new Directory(this.testDirectoryPath);
    String uri = "/sample-text.txt";
    assertEquals("This is a sample text file.\n", directory.read(uri));
  }

}