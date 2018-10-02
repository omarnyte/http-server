package com.omarnyte.mock;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MockSocket extends Socket {
  private InputStream mockInputStream; 
  private MockOutputStream mockOutputStream; 

  public MockSocket(byte[] requestBytes) {
    mockInputStream = new MockInputStream(requestBytes);
    mockOutputStream = new MockOutputStream();
  }
  
  @Override 
  public InputStream getInputStream() {
    return this.mockInputStream;
  }

  @Override 
  public OutputStream getOutputStream() throws IOException {
    return this.mockOutputStream;
  }

  public ByteArrayOutputStream getWrittenBytes() {
    return this.mockOutputStream.getWrittenBytes();
  }

}
