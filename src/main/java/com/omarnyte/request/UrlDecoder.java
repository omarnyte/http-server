package com.omarnyte.request;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlDecoder {
  private final static Map<String, String> PERCENT_ENCODING = Map.ofEntries(
    Map.entry("%3A", ":"),
    Map.entry("%2F", "/"),
    Map.entry("%3F", "?"),
    Map.entry("%23", "#"),
    Map.entry("%5B", "["),
    Map.entry("%5D", "]"),
    Map.entry("%40", "@"),
    Map.entry("%21", "!"),
    Map.entry("%24", "$"),
    Map.entry("%26", "&"),
    Map.entry("%27", "'"),
    Map.entry("%28", "("),
    Map.entry("%29", ")"),
    Map.entry("%2A", "*"),
    Map.entry("%2B", "+"),
    Map.entry("%2C", ","),
    Map.entry("%3B", ";"),
    Map.entry("%3D", "="),
    Map.entry("%25", "%"),
    Map.entry("%20", " "),
    Map.entry("+", " ")
  );
  
  public String decodeString(String encodedString) {
    String percentEncodingRegEx = "%[2-5][0-9A-F]|\\+";
    Pattern pattern = Pattern.compile(percentEncodingRegEx);
    Matcher matcher = pattern.matcher(encodedString);
    return replaceMatchedPatternsInString(matcher, encodedString);
  }

  private String replaceMatchedPatternsInString(Matcher matcher, String encodedString) {
    String decodedString = encodedString;
    
    while (matcher.find()) {
      String encodedCharacter = matcher.group(0);
      String decodedCharacter = PERCENT_ENCODING.getOrDefault(encodedCharacter, encodedCharacter);
      decodedString = decodedString.replace(encodedCharacter, decodedCharacter);
    } 

    return decodedString;
  }

}