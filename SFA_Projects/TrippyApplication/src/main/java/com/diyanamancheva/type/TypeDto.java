package com.diyanamancheva.type;

public class TypeDto {
  private int id;
  private String name;

  public TypeDto(int id, String name){
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
