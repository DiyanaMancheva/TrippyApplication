package com.diyanamancheva.exception;

public class UninstantiableClassException extends Exception {

  private static final String EXCEPTION_MESSAGE = "This class should never be instantiated";

  public UninstantiableClassException() {
    super(EXCEPTION_MESSAGE);
  }
}