package com.diyanamancheva.venue;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class VenueUpdateRequest {
  @NotNull(message = "Address must not be null")
  private String address;
  public VenueUpdateRequest(){}

  public VenueUpdateRequest(String address){
    this.address = address;
  }
  public String getAddress() {
    return address;
  }
}
