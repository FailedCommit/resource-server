package com.maat.resourceserver.exceptions;

public class NonExistentHealthProfileException extends RuntimeException {

  public NonExistentHealthProfileException(String message) {
    super(message);
  }
}
