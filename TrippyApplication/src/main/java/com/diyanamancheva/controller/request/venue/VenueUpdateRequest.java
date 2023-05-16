package com.diyanamancheva.controller.request.venue;

import javax.validation.constraints.NotNull;

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
