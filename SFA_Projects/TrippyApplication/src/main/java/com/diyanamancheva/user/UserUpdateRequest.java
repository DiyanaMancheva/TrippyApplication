package com.diyanamancheva.user;

import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class UserUpdateRequest {
  private String username;
  @Positive(message = "CityId must be greater than 0")
  private int city;
  private String email;
  public UserUpdateRequest(){}
  public UserUpdateRequest(String username, int city, String email){
    this.username = username;
    this.city = city;
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public int getCity() {
    return city;
  }

  public String getEmail() {
    return email;
  }
}
