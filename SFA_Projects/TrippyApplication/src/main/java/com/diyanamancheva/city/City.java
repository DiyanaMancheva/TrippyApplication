package com.diyanamancheva.city;

public class City {
  private int id;
  private String name;

  public City(String name){
    this.name = name;
  }

  public City(int id, String name){
    this(name);
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
