import cucumber.api.java.en.Given;
import cucumber.api.PendingException;
import java.io.File;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DirectoryStepDefs {
  private final static String TEST_DIRECTORY_PATH = System.getProperty("TEST_DIRECTORY_PATH"); 

  @Given("^a file with the name (.+) exists in (.+)$")
  public void a_file_with_the_name_exists_in_the_root_directory(String fileName, String directoryUri) throws Throwable {
    File file = new File(getFileUri(fileName, directoryUri));
    file.createNewFile();
  }

  @Given("^a file with the name (.+) does not exist in (.+)")
  public void a_file_with_the_name_does_not_exist_in(String fileName, String directoryUri) throws Throwable {
    File file = new File(getFileUri(fileName, directoryUri));
    if (file.exists()) file.delete();
    assertFalse(file.exists());
  }

  @Given("^a directory (.+) exists$")
  public void a_directory_POSTed_exists(String directoryURI) throws Throwable {
    File directory = new File(TEST_DIRECTORY_PATH + directoryURI);
    if (!directory.isDirectory()) directory.mkdirs();
    assertTrue(directory.exists());
  }

  private String getFileUri(String fileName, String directoryUri) {
    return TEST_DIRECTORY_PATH + directoryUri + "/" + fileName;
  } 

}
