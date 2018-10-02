package com.omarnyte.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.omarnyte.request.Request;

public class TestUtil {
  private final static String VERSION = "1.1";

  public static Request buildRequestToUri(String method, String uri) {
    return new Request.Builder() 
                      .method(method) 
                      .uri(uri) 
                      .version(VERSION) 
                      .build(); 
  }
  
  public static Request buildRequestToUriWithBody(String method, String uri, String body) {
    return new Request.Builder() 
                      .method(method) 
                      .uri(uri) 
                      .version(VERSION) 
                      .body(body)
                      .build(); 
  }
  
  public static String createRootHtmlFromFileNames(String[] fileNames) {
    String expectedHtml = "";
    for (String fileName : fileNames) {
      expectedHtml += String.format(
        "<a href=\"/%s\">%s</a>" + 
        "<br>", fileName, fileName);
    }

    return expectedHtml;
  }

  public static String removeLeadingParenthesesFromUri(String uri) {
    int idxOfFirstCharacter = 1;
    return uri.substring(idxOfFirstCharacter);
  }

  public static String getFormattedTime(String dateTimepattern) {
    DateFormat dateFormat = new SimpleDateFormat(dateTimepattern);
    return dateFormat.format(new Date());
  }

  public static byte[] readFile(String uri) {
    Path filePath = Paths.get(uri);
    byte[] fileBytes = null;
    try {
      fileBytes = Files.readAllBytes(filePath);
    } catch (IOException e) {
      System.err.println("Could not read file: " + uri);
      e.printStackTrace();
    }
    return fileBytes;
  }

}
