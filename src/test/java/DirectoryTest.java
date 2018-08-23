import java.io.File;
import java.io.IOException; 
import java.util.Arrays;
import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertFalse; 
import static org.junit.Assert.assertTrue; 
import org.junit.BeforeClass; 
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test; 

public class DirectoryTest {
  private final static String EMPTY_TEXT_FILE_URI = "/empty-file.txt";
  private final static String NONEXISTENT_FILE_URI = "/does-not-exist.txt";
  private final static String TEMP_DIRECTORY_PATH = System.getProperty("user.dir") + "/src/test/java/TestDirectory";
  private final static String TEMP_SUBDIRECTORY_URI = "/subdirectory";
  private final static String TEXT_FILE_CONTENT = "This is a sample text file.";
  private final static String TEXT_FILE_URI = "/text-file.txt";
  private final static String TO_BE_DELETED_URI = "/to-be-deleted.txt";

  private static Directory directory;

  @BeforeClass
  public static void setUp() throws IOException, NonexistentDirectoryException {
    TempDirectory temp = new TempDirectory(TEMP_DIRECTORY_PATH);
    TempDirectory tempSubdirectory = new TempDirectory(TEMP_DIRECTORY_PATH + TEMP_SUBDIRECTORY_URI);
    temp.createEmptyFile(EMPTY_TEXT_FILE_URI);
    temp.createEmptyFile(TO_BE_DELETED_URI);
    temp.createFileWithContent(TEXT_FILE_URI, TEXT_FILE_CONTENT);

    directory = new Directory(TEMP_DIRECTORY_PATH); 
  }
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();  

  @Test 
  public void throwsNonexistentDirectoryExceptionWithInvalidPath() throws NonexistentDirectoryException {
    thrown.expect(NonexistentDirectoryException.class);
    Directory directory = new Directory(NONEXISTENT_FILE_URI);
  }

  @Test 
  public void returnsTrueWhenPassedADirectoryUri() {
    assertTrue(directory.isDirectory(TEMP_SUBDIRECTORY_URI));
  }
  
  @Test 
  public void returnsTrueWhenPassedAFileUri() {
    assertTrue(directory.isFile(EMPTY_TEXT_FILE_URI));
  }
  
  @Test 
  public void listsRootDirectoryContentAsArray() {
    String[] directoryContent = directory.listContent();
    String emptyTextFileName = TestUtil.removeLeadingParenthesesFromUri(EMPTY_TEXT_FILE_URI);
    String textFileName = TestUtil.removeLeadingParenthesesFromUri(TEXT_FILE_URI);
    assertTrue(Arrays.asList(directoryContent).contains(emptyTextFileName));
    assertTrue(Arrays.asList(directoryContent).contains(textFileName));
  }
  
  @Test
  public void returnsTrueWhenFileExists() {
    assertEquals(true, directory.existsInStore(TEXT_FILE_URI));
  }

  @Test
  public void returnsFalseWhenFileDoesNotExist() {
    assertEquals(false, directory.existsInStore(NONEXISTENT_FILE_URI));
  }

  @Test 
  public void readsFileContentAndRespondsWithBytes() {
    byte[] expectedResponseInBytes = TEXT_FILE_CONTENT.getBytes();
    assertTrue(Arrays.equals(expectedResponseInBytes, directory.readFile(TEXT_FILE_URI)));
  }

  @Test 
  public void returnsTheCorrectFileTypeForTxt() {
    String expectedContentType = "text/plain";
    assertEquals(expectedContentType, directory.getFileType(TEXT_FILE_URI));
  }

  @Test 
  public void returnsTrueIfFileIsCreatedInDirectory() {
    String uri = "/sample-posted-file.txt";
    byte[] content = "This is a sample poste file".getBytes();
    boolean wasCreated = directory.createFileWithContent(uri, content);

    File file = new File(System.getProperty("user.dir") + "/src/test/java/TestDirectory" + uri);

    assertTrue(wasCreated); 
    assertTrue(file.exists()); 
    file.delete();
  }

  @Test 
  public void returnsTrueIfFileWasDeleted() {
    boolean wasDeleted = directory.deleteFile(TO_BE_DELETED_URI);
    File shouldHaveBeenDeleted = new File(TEMP_DIRECTORY_PATH + TO_BE_DELETED_URI); 

    assertTrue(wasDeleted);
    assertFalse(shouldHaveBeenDeleted.exists());
  }
  
}
