package com.diyanamancheva.exception;

public class DatabaseConnectivityException extends RuntimeException{

  public DatabaseConnectivityException(Throwable couse){
    super(couse);
  }
}
