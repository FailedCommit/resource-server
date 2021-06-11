package com.maat.resourceserver.exceptions;

public class HealthProfileAlreadyExistsException extends RuntimeException {

  public HealthProfileAlreadyExistsException(String message) {
    super(message);
  }
}
