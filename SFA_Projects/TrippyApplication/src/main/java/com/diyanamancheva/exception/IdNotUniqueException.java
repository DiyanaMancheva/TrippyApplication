package com.diyanamancheva.exception;

public class IdNotUniqueException extends RuntimeException{

  public IdNotUniqueException(String message){
    super(message);
  }
}
