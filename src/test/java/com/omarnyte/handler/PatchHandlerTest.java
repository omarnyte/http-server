package com.omarnyte.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

import com.omarnyte.exception.NonexistentDirectoryException;
import com.omarnyte.http.MessageHeader;
import com.omarnyte.http.MimeType;
import com.omarnyte.jsonpatch.JsonPatchOperation;
import com.omarnyte.jsonpatch.JsonPatchParser;
import com.omarnyte.mock.MockDirectory;
import com.omarnyte.mock.MockJsonPatchOperation;
import com.omarnyte.mock.MockJsonPatchParser;
import com.omarnyte.request.HttpMethod;
import com.omarnyte.request.Request;
import com.omarnyte.response.HttpStatusCode;
import com.omarnyte.response.Response;

public class PatchHandlerTest {
  private final static String JSON_FILE_URI = "/to-be-patched.json";
  private final static String JSON_FILE_ORIGINAL_CONTENT = "ORIGINAL JSON FILE CONTENT";
  private final static String JSON_PATCH_TYPE = "application/json-patch+json";
  private final static String JSON_PATCH_OPERATION_RESULT = "UPDATE BY OPERATION";
  
  private static Handler patchHandler;
  private static Response successfulResponseToPatch;

  @BeforeClass
  public static void setUpOnce() throws IOException, NonexistentDirectoryException {
    MockDirectory mockDirectory = setUpMockDirectory();
    JsonPatchParser mockJsonPatchParser = setUpMockJsonPatchParser();
    patchHandler = new PatchHandler(mockDirectory, mockJsonPatchParser); 

    Request request = buildPatchRequestToJsonFileWithJsonPatchContent();
    successfulResponseToPatch = patchHandler.generateResponse(request);
  }

  @Test 
  public void returns404NotFoundForPatchRequestToNonexistentResource() {
    Request request = buildPatchRequestToNonexistentJsonFile();
    Response response = patchHandler.generateResponse(request);

    int expectedStatusCode = HttpStatusCode.NOT_FOUND;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  @Test 
  public void returns415UnsupportedMediaTypeForPatchRequestToJsonGivenANonJsonBody() {
    Request request = buildPatchRequestToJsonFileWithPlainTextContent();
    Response response = patchHandler.generateResponse(request);

    int expectedStatusCode = HttpStatusCode.UNSUPPORTED_MEDIA_TYPE;
    int actualStatusCode = response.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = response.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  @Test 
  public void returns200OkAfterSuccessfulPatch() {
    int expectedStatusCode = HttpStatusCode.OK;
    int actualStatusCode = successfulResponseToPatch.getStatusCode();
    assertEquals(expectedStatusCode, actualStatusCode);

    String expectedReasonPhrase = HttpStatusCode.getReasonPhrase(expectedStatusCode);
    String actualReasonPhrase = successfulResponseToPatch.getReasonPhrase();
    assertEquals(expectedReasonPhrase, actualReasonPhrase);
  }

  @Test 
  public void returnsUpdatedContentAfterSuccessfulPatch() {
    assertEquals(JSON_PATCH_OPERATION_RESULT, new String(successfulResponseToPatch.getMessageBody()));
  }
  
  private static MockDirectory setUpMockDirectory() throws NonexistentDirectoryException {
    List<String> files = setUpMockFiles();   
    HashMap<String, String> fileContents = setUpMockFileContents();
    Map<String, String> fileTypes = setUpMockFileTypes();
    return new MockDirectory(files, fileContents, fileTypes);
  }

  private static List<String> setUpMockFiles() {
    ArrayList<String> files = new ArrayList<String>();
    files.add(JSON_FILE_URI);
    return files;
  }

  private static HashMap<String, String> setUpMockFileContents() {
    HashMap<String, String> fileContents = new HashMap<String, String>();
    fileContents.put(JSON_FILE_URI, JSON_FILE_ORIGINAL_CONTENT);
    return fileContents;
  }

  private static Map<String, String> setUpMockFileTypes() {
    return Map.ofEntries(
      Map.entry(JSON_FILE_URI, MimeType.JSON)
    );
  }

  private static JsonPatchParser setUpMockJsonPatchParser() {
    ArrayList<JsonPatchOperation> mockOperations = new ArrayList<JsonPatchOperation>();
    mockOperations.add(new MockJsonPatchOperation(JSON_PATCH_OPERATION_RESULT));
    return new MockJsonPatchParser(mockOperations);
  }

  private Request buildPatchRequestToNonexistentJsonFile() {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put(MessageHeader.CONTENT_TYPE, JSON_PATCH_TYPE);
    String jsonPatchBody = "JSON PATCH BODY";
    return new Request.Builder() 
                      .method(HttpMethod.PATCH) 
                      .uri("does-not-exist.json") 
                      .headers(headers)
                      .body(jsonPatchBody)
                      .build(); 
  }

  private Request buildPatchRequestToJsonFileWithPlainTextContent() {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put(MessageHeader.CONTENT_TYPE, MimeType.PLAIN_TEXT);
    String plainTextBody = "plain text body";
    return new Request.Builder() 
                      .method(HttpMethod.PATCH) 
                      .uri(JSON_FILE_URI) 
                      .headers(headers)
                      .body(plainTextBody)
                      .build(); 
  }

  private static Request buildPatchRequestToJsonFileWithJsonPatchContent() {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put(MessageHeader.CONTENT_TYPE, JSON_PATCH_TYPE);
    String jsonPatchBody = "JSON PATCH BODY";
    return new Request.Builder() 
                      .method(HttpMethod.PATCH) 
                      .uri(JSON_FILE_URI) 
                      .headers(headers)
                      .body(jsonPatchBody)
                      .build(); 
  }

}
