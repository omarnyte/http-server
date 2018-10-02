package com.omarnyte.mock;

import com.omarnyte.request.UrlDecoder;

public class MockUrlDecoder extends UrlDecoder {

  public String decodeString(String encodedString) {
    return encodedString;
  }
  
}