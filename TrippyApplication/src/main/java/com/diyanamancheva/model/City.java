package com.diyanamancheva.model;

import com.diyanamancheva.model.Venue;

import java.util.ArrayList;
import java.util.List;

public class City {
  private int id;
  private String name;

  private List<Venue> venues;

  public City(String name){
    this.name = name;
  }

  public City(int id, String name){
    this(name);
    this.id = id;
    this.venues = new ArrayList<>();
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
