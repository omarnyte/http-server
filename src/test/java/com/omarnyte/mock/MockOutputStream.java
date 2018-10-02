package com.omarnyte.mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MockOutputStream extends OutputStream {
  private ByteArrayOutputStream writtenBytes = new ByteArrayOutputStream(); 
  
  @Override 
  public void write​(int b) throws IOException {
    this.writtenBytes.write(b);
  }

  public ByteArrayOutputStream getWrittenBytes() {
    return this.writtenBytes;
  }

}
