package com.omarnyte;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test;

import com.omarnyte.CliParser;
import com.omarnyte.exception.MissingFlagException;
import com.omarnyte.exception.UnsupportedFlagException;

public class CLIParserTest {
  private String[] argsWithDirectory = { "-dir", "some/path" };
  private String[] argsWithPortAndDirectory = { "-port", "7777", "-dir", "some/path" };

  private String[] emptyArgs = { };

  @Rule
  public ExpectedException thrown = ExpectedException.none();  
  
  @Test 
  public void returnsDefaultPortNumberIfNoneIsProvided() throws UnsupportedFlagException {
    CliParser parser = new CliParser(this.argsWithDirectory);
    int defaultPortNumber = 9999;
    assertEquals(9999, parser.getPortNumberOrDefault(9999));
  }

  @Test 
  public void returnsPortNumberIfOneIsProvided() throws UnsupportedFlagException {
    CliParser parser = new CliParser(this.argsWithPortAndDirectory);
    int defaultPortNumber = 9999;
    assertEquals(7777, parser.getPortNumberOrDefault(9999));
  }
  
  @Test 
  public void throwsMissingFlagExceptionWhenNoStoreProvided() throws MissingFlagException, UnsupportedFlagException {
    CliParser parser = new CliParser(this.emptyArgs);

    thrown.expect(MissingFlagException.class);
    String storeFlag = parser.getStoreFlag();
  }
  
  @Test 
  public void returnsCorrectStoreFlag() throws MissingFlagException, UnsupportedFlagException {
    CliParser parser = new CliParser(this.argsWithPortAndDirectory);
    assertEquals("-dir", parser.getStoreFlag());
  }

  @Test 
  public void returnsDirectory() throws UnsupportedFlagException {
    CliParser parser = new CliParser(this.argsWithPortAndDirectory);
    assertEquals("some/path", parser.getDirectory());
  }

  @Test 
  public void printsValidFlags() throws UnsupportedFlagException {
    CliParser parser = new CliParser(this.argsWithPortAndDirectory);
    assertTrue(parser.printValidFlags().contains("-dir"));
  }

  @Test 
  public void printsValidStoreFlags() throws UnsupportedFlagException {
    CliParser parser = new CliParser(this.argsWithPortAndDirectory);
    assertTrue(parser.printValidFlags().contains("-dir"));
  }
  
}
