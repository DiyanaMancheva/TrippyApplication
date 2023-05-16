package com.diyanamancheva.exception;

public class UserExistsException extends RuntimeException{

  public UserExistsException(String message){
    super(message);
  }
}
