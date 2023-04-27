package com.diyanamancheva.venue;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class VenueRequest {
  @NotNull(message = "Name must not be null")
  String name;
  @Pattern(regexp = "^[a-zA-Z]+$", message = "Type must not be null or contain numbers")
  String type;
  @Pattern(regexp = "^[a-zA-Z]+.+(?:[\\s-][a-zA-Z]+)*$", message = "City name must not be null or contain numbers")
  String city;
  @NotNull(message = "Address must not be null")
  String address;

  public VenueRequest(){}

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getCity() {
    return city;
  }

  public String getAddress() {
    return address;
  }
}
