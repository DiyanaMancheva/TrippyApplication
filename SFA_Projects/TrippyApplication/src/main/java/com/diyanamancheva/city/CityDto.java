package com.diyanamancheva.city;

public class CityDto {
  private int id;
  private String name;

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
