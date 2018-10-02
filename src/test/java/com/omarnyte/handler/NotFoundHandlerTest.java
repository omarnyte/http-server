package com.omarnyte.handler;

import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Test; 

import com.omarnyte.Directory;
import com.omarnyte.exception.NonexistentDirectoryException;
import com.omarnyte.mock.MockDirectory;
import com.omarnyte.request.HttpMethod;
import com.omarnyte.request.Request;
import com.omarnyte.response.HttpStatusCode;
import com.omarnyte.response.Response;
import com.omarnyte.testutil.TestUtil;

public class NotFoundHandlerTest {
  private final static String NONEXISTENT_URI =  "/does-not-exist.txt";

  private static Handler notFoundHandler;
  
  @BeforeClass 
  public static void setUp() throws NonexistentDirectoryException {
    Directory mockDirectory = new MockDirectory();
    notFoundHandler = new NotFoundHandler(mockDirectory);
  }

  @Test 
  public void returns404NotFoundForGetRequests() {
    Request request = TestUtil.buildRequestToUri(HttpMethod.GET, NONEXISTENT_URI);
    Response response = notFoundHandler.generateResponse(request);

    int expectedStatusCode = HttpStatusCode.NOT_FOUND;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  @Test 
  public void returns404NotFoundForPatchRequests() {
    Request request = TestUtil.buildRequestToUri(HttpMethod.PATCH, NONEXISTENT_URI);
    Response response = notFoundHandler.generateResponse(request);

    int expectedStatusCode = HttpStatusCode.NOT_FOUND;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  @Test 
  public void returns201CreatedForPutRequestToResourceThatDoesNotExist() {
    String body = "body";
    Request request = TestUtil.buildRequestToUriWithBody(HttpMethod.PUT, NONEXISTENT_URI, body);
    Response response = notFoundHandler.generateResponse(request);

    int expectedStatusCode = HttpStatusCode.CREATED;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

}
