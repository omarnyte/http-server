import cucumber.api.java.en.Given;
import cucumber.api.PendingException;
import java.io.File;
import static org.junit.Assert.assertFalse;

public class DirectoryStepDefs {
  private final static String TEST_DIRECTORY_PATH = System.getProperty("TEST_DIRECTORY_PATH"); 

  @Given("^a file with the name (.+) exists in the root directory$")
  public void a_file_with_the_name_exists_in_the_root_directory(String fileName) throws Throwable {
    File file = new File(TEST_DIRECTORY_PATH + "/" + fileName);
    file.createNewFile();
  }

  @Given("^a file with the name (.+) does not exist in the root directory$")
  public void a_file_with_the_name_does_not_exist_in_the_root_directory(String fileName) throws Throwable {
    File file = new File(TEST_DIRECTORY_PATH + "/" + fileName);
    assertFalse(file.exists());
  }
  
}
