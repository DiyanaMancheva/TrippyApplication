package com.diyanamancheva.type;

public class Type {
  private int id;
  private String name;

  public Type(String name){
    this.name = name;
  }

  public Type(int id, String name){
    this(name);
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
