package com.omarnyte.exception;

public class NonexistentDirectoryException extends Exception {

  public NonexistentDirectoryException(String directory) {
    super(String.format("%s does not exist.", directory));
  }
}