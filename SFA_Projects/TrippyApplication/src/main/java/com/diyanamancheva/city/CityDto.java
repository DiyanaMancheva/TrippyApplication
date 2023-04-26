package com.diyanamancheva.city;

import javax.validation.constraints.Pattern;

public class CityDto {
  @Pattern(regexp = "^[a-zA-Z]+.+(?:[\\s-][a-zA-Z]+)*$", message = "Name must not be null or contain numbers")
  private String name;

  public CityDto(){}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
