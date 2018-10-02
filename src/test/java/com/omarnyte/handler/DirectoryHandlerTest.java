package com.omarnyte.handler;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

import com.omarnyte.Directory;
import com.omarnyte.exception.NonexistentDirectoryException;
import com.omarnyte.http.MessageHeader;
import com.omarnyte.request.HttpMethod;
import com.omarnyte.request.Request;
import com.omarnyte.response.HttpStatusCode;
import com.omarnyte.response.Response;
import com.omarnyte.testutil.TempDirectory;
import com.omarnyte.testutil.TestUtil;

public class DirectoryHandlerTest {
  private final static String HTML_FILE_URI = "/html-file.html";
  private final static String TEXT_FILE_URI = "/text-file.txt";
  private final static String URI = "/";
  
  private static Handler handler; 
  private static Response responseToOptionsRequest;
  
  @BeforeClass
  public static void setup () throws IOException, NonexistentDirectoryException {
    String tempDirectoryPath = System.getProperty("user.dir") + "/src/test/java/com/omarnyte/handler/DirectoryHandlerTestDirectory";

    TempDirectory temp = new TempDirectory(tempDirectoryPath);
    temp.createEmptyFile(TEXT_FILE_URI);
    temp.createEmptyFile(HTML_FILE_URI);

    Directory directory = new Directory(tempDirectoryPath); 
    handler = new DirectoryHandler(directory); 

    Request optionsRequest = new Request.Builder().method(HttpMethod.OPTIONS).build();
    responseToOptionsRequest = handler.generateResponse(optionsRequest);
  }

  @Test 
  public void returns200OkForOptionsRequest() {
    int expectedStatusCode = HttpStatusCode.OK;
    int actualStatusCode = responseToOptionsRequest.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = responseToOptionsRequest.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }
  
  @Test 
  public void returnsSupportedMethodsInAllowHeaderForOptionsRequest() {
    String expectedHeaderVal = "GET, HEAD, OPTIONS";
    String actualHeaderVal = responseToOptionsRequest.getHeader(MessageHeader.ALLOW);
    assertEquals(expectedHeaderVal, actualHeaderVal);
  }
    
  @Test 
  public void returneContentsOfDirectoryAsHtml() {
    Request request = TestUtil.buildRequestToUri(HttpMethod.GET, URI);

    String[] files = { TestUtil.removeLeadingParenthesesFromUri(HTML_FILE_URI),
                       TestUtil.removeLeadingParenthesesFromUri(TEXT_FILE_URI) 
                     };
    String expectedHtml = TestUtil.createRootHtmlFromFileNames(files);
    String messageBody = new String(handler.generateResponse(request).getMessageBody());
    assertEquals(expectedHtml, messageBody);
  }
  
  @Test 
  public void returnsOnlyHeadersForHeadRequest() {  
    Request request = TestUtil.buildRequestToUri(HttpMethod.HEAD, URI);
    Response response = handler.generateResponse(request);
    String messageBody = new String(response.getMessageBody());
    assertEquals(HttpStatusCode.OK, response.getStatusCode()); 
    assertEquals("", messageBody); 
  }
  
  @Test
  public void return405MethodNotAllowedWithUnallowedMethod() {
    String method = "NOT_ALLOWED";
    Request request = TestUtil.buildRequestToUri(method, URI);
    Response response = handler.generateResponse(request);

    int expectedStatusCode = HttpStatusCode.METHOD_NOT_ALLOWED;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

}