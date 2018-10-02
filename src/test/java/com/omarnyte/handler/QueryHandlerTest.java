package com.omarnyte.handler;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;

import com.omarnyte.http.MessageHeader;
import com.omarnyte.request.HttpMethod;
import com.omarnyte.request.Request;
import com.omarnyte.request.UrlDecoder;
import com.omarnyte.mock.MockUrlDecoder;
import com.omarnyte.response.HttpStatusCode;
import com.omarnyte.response.Response;

public class QueryHandlerTest {
  private final static String FIRST_KEY = "aKey"; 
  private final static String FIRST_VALUE = "aValue"; 
  private final static String SECOND_KEY = "anotherKey"; 
  private final static String SECOND_VALUE = "anotherValue"; 
  private final static String METHOD = "GET"; 
  private final static String URI = "api/query"; 

  private static QueryHandler handler;
  private static Response responseToOptionsRequest;
  
  @BeforeClass
  public static void setUp() {
    UrlDecoder mockUrlDecoder = new MockUrlDecoder();
    handler = new QueryHandler(mockUrlDecoder);

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
  public void returns400BadRequestWithMalformedQuery() {
    String query = "aKeyWithoutAValue";
    Request request = buildRequest(METHOD, URI, query);
    Response response = handler.generateResponse(request);
    
    int expectedStatusCode = HttpStatusCode.BAD_REQUEST;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }
  
  @Test 
  public void returnsSingleQueryParameters() {
    String query = FIRST_KEY + "=" + FIRST_VALUE;
    Request request = buildRequest(METHOD, URI, query);
    
    String expectedString = FIRST_KEY + " : " + FIRST_VALUE + "\n";
    String actualString = new String(handler.generateResponse(request).getMessageBody());
    assertEquals(expectedString, actualString);
  }

  @Test 
  public void returnsMultipleQueryParameters() {
    String query = String.format("%s=%s&%s=%s", FIRST_KEY, FIRST_VALUE, SECOND_KEY, SECOND_VALUE);
    Request request = buildRequest(METHOD, URI, query);
    
    String expectedString = String.format("%s : %s\n%s : %s\n", FIRST_KEY, FIRST_VALUE, SECOND_KEY, SECOND_VALUE);
    String actualString = new String(handler.generateResponse(request).getMessageBody());
    assertEquals(expectedString, actualString);
  }

  private Request buildRequest(String method, String uri, String query) {
    return new Request.Builder()
                      .method(method)
                      .uri(uri)
                      .query(query)
                      .build();
  }
  
}