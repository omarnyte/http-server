package com.omarnyte.handler;

import java.util.HashMap;
import static org.junit.Assert.assertEquals; 
import org.junit.BeforeClass; 
import org.junit.Test; 

import com.omarnyte.Directory;
import com.omarnyte.exception.NonexistentDirectoryException;
import com.omarnyte.http.MessageHeader;
import com.omarnyte.mock.MockDirectory;
import com.omarnyte.request.HttpMethod;
import com.omarnyte.request.Request;
import com.omarnyte.response.HttpStatusCode;
import com.omarnyte.response.Response;

public class PeopleHandlerTest {
  private final static String SUPPORTED_MEDIA_TYPE = "application/json";
  private final static HashMap<String, String> HEADERS_WITH_SUPPORTED_MEDIA = buildHeadersWithSupportedMediaType();
  
  private static Handler peopleHandler;
  private static Response responseToOptionsRequest;

  @BeforeClass
  public static void setUp() throws NonexistentDirectoryException {
    Directory mockDirectory = new MockDirectory();
    peopleHandler = new PeopleHandler(mockDirectory);

    Request optionsRequest = new Request.Builder().method(HttpMethod.OPTIONS).build();
    responseToOptionsRequest = peopleHandler.generateResponse(optionsRequest);
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
    String expectedHeaderVal = "OPTIONS, POST";
    String actualHeaderVal = responseToOptionsRequest.getHeader(MessageHeader.ALLOW);
    assertEquals(expectedHeaderVal, actualHeaderVal);
  }
  
  @Test 
  public void returns415UnsupportedMediaTypeWithIncompatibleMediaType() {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put(MessageHeader.CONTENT_TYPE, "unsupported/type");
    Request request = buildRequestWithHeaders(HttpMethod.POST, headers);
    Response response = peopleHandler.generateResponse(request);
                                 
    int expectedStatusCode = HttpStatusCode.UNSUPPORTED_MEDIA_TYPE;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  @Test 
  public void returns201CreatedWithCompatibleMediaType() {
    Request request = buildRequestWithHeaders(HttpMethod.POST, HEADERS_WITH_SUPPORTED_MEDIA);
    Response response = peopleHandler.generateResponse(request);
                                 
    int expectedStatusCode = HttpStatusCode.CREATED;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  @Test 
  public void returns405MethodNotAllowedWithUnallowedMethod() {
    String method = "NOT_ALLOWED";
    Request request = buildRequestWithHeaders(method, HEADERS_WITH_SUPPORTED_MEDIA);
    Response response = peopleHandler.generateResponse(request);
                                 
    int expectedStatusCode = HttpStatusCode.METHOD_NOT_ALLOWED;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  private static HashMap<String, String> buildHeadersWithSupportedMediaType() {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put(MessageHeader.CONTENT_TYPE, SUPPORTED_MEDIA_TYPE);
    return headers;
  }

  private static Request buildRequestWithHeaders(String method, HashMap<String, String> headers) {
   return new Request.Builder()
                     .method(method)
                     .headers(headers)
                     .build();
  }

}
