package com.diyanamancheva.dto.type;

public class TypeDto {
  private final int id;
  private final String name;

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
