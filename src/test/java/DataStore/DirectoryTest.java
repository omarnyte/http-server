import java.io.File; 
import java.io.IOException; 
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test; 

public class DirectoryTest {
  private static Directory directory;

  @BeforeClass
  public static void setUp() throws IOException, NonexistentDirectoryException {
    String tempDirectoryPath = System.getProperty("user.dir") + "/src/test/java/DataStore/TestDirectory";

    TempDirectory temp = new TempDirectory(tempDirectoryPath);
    temp.createEmptyFile("empty-file.txt");
    temp.createFileWithContent("text-file.txt", "This is a sample text file.");

    directory = new Directory(tempDirectoryPath); 
  }
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();  

  @Test 
  public void throwsNonexistentDirectoryExceptionWithInvalidPath() throws NonexistentDirectoryException {
    String nonexistentPath = "path/that/does/not/exist";

    thrown.expect(NonexistentDirectoryException.class);
    Directory directory = new Directory(nonexistentPath);
  }

  @Test 
  public void listsDirectoryContent() {
    assertEquals("empty-file.txt\n" +
                 "text-file.txt\n", directory.listContent());
  }
  
  @Test
  public void returnsTrueWhenFileExists() {
    String uri = "/text-file.txt";
    assertEquals(true, directory.existsInStore(uri));
  }

  @Test
  public void returnsFalseWhenFileExists() {
    String uri = "/does-not-exist.txt";
    assertEquals(false, directory.existsInStore(uri));
  }

  @Test
  public void readsFileContent() throws NonexistentDirectoryException {
    String uri = "/text-file.txt";
    assertEquals("This is a sample text file.\n", directory.read(uri));
  }

}
