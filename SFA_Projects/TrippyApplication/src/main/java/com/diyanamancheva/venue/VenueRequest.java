package com.diyanamancheva.venue;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class VenueRequest {
  @NotNull(message = "Name must not be null")
  private String name;
  @Positive(message = "VenueId must be greater than 0")
  private int type;
  @Positive(message = "VenueId must be greater than 0")
  private int city;
  @NotNull(message = "Address must not be null")
  private String address;
  private float rating;

  public VenueRequest(){}

  public VenueRequest(String name, int type, int city,
                      String address, float rating){
    this.name = name;
    this.type = type;
    this.city = city;
    this.address = address;
    this.rating = rating;
  }

  public String getName() {
    return name;
  }

  public int getType() {
    return type;
  }

  public int getCity() {
    return city;
  }

  public String getAddress() {
    return address;
  }

  public float getRating() {
    return rating;
  }
}
