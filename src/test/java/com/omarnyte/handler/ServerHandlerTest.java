package com.omarnyte.handler;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

import com.omarnyte.http.MessageHeader;
import com.omarnyte.request.HttpMethod;
import com.omarnyte.request.Request;
import com.omarnyte.response.Response;
import com.omarnyte.response.HttpStatusCode;

public class ServerHandlerTest {
  private final static String FIRST_SUPPORTED_METHOD = "SUPPORTED_METHOD";
  private final static String SECOND_SUPPORTED_METHOD = "ANOTHER_SUPPORTED_METHOD";
  private final static String URI = "*";
  
  private static Response responseToOptionsRequest;

  @BeforeClass 
  public static void setUp() {
    List<String> mockSupportedMethods = Arrays.asList(FIRST_SUPPORTED_METHOD, SECOND_SUPPORTED_METHOD);
    Handler serverHandler = new ServerHandler(mockSupportedMethods);
    Request optionsRequest = buildOptionsRequest();
    responseToOptionsRequest = serverHandler.generateResponse(optionsRequest);
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
    String expectedHeaderVal = String.format("%s, %s", FIRST_SUPPORTED_METHOD, SECOND_SUPPORTED_METHOD);
    String actualHeaderVal = responseToOptionsRequest.getHeader(MessageHeader.ALLOW);
    assertEquals(expectedHeaderVal, actualHeaderVal);
  }

  private static Request buildOptionsRequest() {
    return new Request.Builder()
                      .method(HttpMethod.OPTIONS)
                      .uri(URI)
                      .build();
  }

}