package com.diyanamancheva.util;

import java.util.Scanner;

public final class ConsoleReader {

  private static final String INVALID_INT = "%s is not a number!\nTry again: ";
  private static final String INVALID_RANGE_MESSAGE = "Min value cannot exceed max value";

  private static final String NOT_WITHIN_RANGE_MESSAGE = "Input must be between %d and %d\nTry again: ";
  private static final Scanner scanner = new Scanner(System.in);

  private ConsoleReader(){
    throw new UnsupportedOperationException();
  }

  public static String readString(){
    return scanner.nextLine();
  }

  public static int readInt(){

    while(!scanner.hasNext()){
      String input = scanner.next();
      System.out.printf(INVALID_INT, input);
    }

    int input = scanner.nextInt();
    scanner.nextLine();
    return input;
  }

  public static int readWithinRange(int minValue, int maxValue){

    int input;
    boolean isWithinRange;
    if(minValue >= maxValue){
      throw new IllegalArgumentException(INVALID_RANGE_MESSAGE);
    }
    do {
      input = readInt();
      isWithinRange = input >= minValue && input <= maxValue;
      if (!isWithinRange) {
        System.out.printf(NOT_WITHIN_RANGE_MESSAGE, minValue, maxValue);
      }
    } while (!isWithinRange);
    return input;
  }

}

