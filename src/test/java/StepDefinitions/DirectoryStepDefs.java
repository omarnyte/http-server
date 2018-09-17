import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.PendingException;
import java.io.File;
import java.io.FileOutputStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DirectoryStepDefs {
  private final static String TEST_DIRECTORY_PATH = System.getProperty("TEST_DIRECTORY_PATH"); 

  private File file;

  @Given("^a file with the name (.+) exists in (.+)$")
  public void a_file_with_the_name_exists_in_the_root_directory(String fileName, String directoryUri) throws Throwable {
    File file = new File(getFileUri(fileName, directoryUri));
    file.createNewFile();
    this.file = file;
    assertTrue(file.exists());
  }

  @Given("^a file with the name (.+) does not exist in (.+)")
  public void a_file_with_the_name_does_not_exist_in(String fileName, String directoryUri) throws Throwable {
    File file = new File(getFileUri(fileName, directoryUri));
    if (file.exists()) file.delete();
    assertFalse(file.exists());
  }

  @Given("^the file contains the content$")
  public void the_file_contains_the_content(String content) throws Throwable {
    byte[] contentBytes = content.getBytes();
    FileOutputStream outputStream = new FileOutputStream(this.file);
    outputStream.write(contentBytes);
  }

  @Given("^a directory (.+) exists$")
  public void a_directory_POSTed_exists(String directoryURI) throws Throwable {
    File directory = new File(TEST_DIRECTORY_PATH + directoryURI);
    if (!directory.isDirectory()) directory.mkdirs();
    assertTrue(directory.exists());
  }

  @Then("^the file with the name (.+) in (.+) should contain the content \"([^\"]*)\"$")
  public void the_file_with_the_name_will_be_created_and_modified_txt_in_should_contain_the_content(String fileName, String directoryURI, String content) throws Throwable {
    String actualContent = new String(TestUtil.readFile(getFileUri(fileName, directoryURI)));
    String expectedContent = content;
    assertEquals(expectedContent, actualContent);
  }

  private String getFileUri(String fileName, String directoryURI) {
    return TEST_DIRECTORY_PATH + directoryURI + "/" + fileName;
  } 

}
