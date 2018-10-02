package com.omarnyte.http;

import java.util.Map;

public class MimeType {
  public final static String JSON = "application/json";
  public final static String JSON_PATCH = "application/json-patch+json";
  public final static String PLAIN_TEXT = "text/plain";

  private static final Map<String, String> extensions = Map.ofEntries(
    Map.entry(JSON, ".json"),
    Map.entry(PLAIN_TEXT, ".txt")
  );
  
  public static final String getExtension(String type) {
    return extensions.get(type);
  }

}
