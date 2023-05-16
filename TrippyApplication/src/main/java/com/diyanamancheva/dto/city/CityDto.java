package com.diyanamancheva.dto.city;

public class CityDto {
  private final int id;
  private final String name;

  public CityDto(int id, String name){
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
