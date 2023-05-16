package com.diyanamancheva.controller.request.city;

import javax.validation.constraints.Pattern;

public class CityRequest {
  @Pattern(regexp = "^[a-zA-Z]+.+(?:[\\s-][a-zA-Z]+)*$", message = "Name must not be null or contain numbers")
  private String name;

  public CityRequest(){}

  public CityRequest(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
