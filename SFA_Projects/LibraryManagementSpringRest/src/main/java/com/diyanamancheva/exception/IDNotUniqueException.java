package com.diyanamancheva.exception;

public class IDNotUniqueException extends RuntimeException{

  public IDNotUniqueException(String message){
    super(message);
  }
}
