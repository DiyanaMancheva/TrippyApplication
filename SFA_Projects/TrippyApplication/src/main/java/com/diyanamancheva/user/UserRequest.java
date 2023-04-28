package com.diyanamancheva.user;

import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class UserRequest {
  private String username;
  @Positive(message = "CityId must be greater than 0")
  private int cityId;
  private String email;
  private LocalDate joinDate;

  public UserRequest(){}

  public UserRequest(String username, int cityId, String email,
                     LocalDate joinDate){
    this.username = username;
    this.cityId = cityId;
    this.email = email;
    this.joinDate = joinDate;
  }

  public String getUsername() {
    return username;
  }

  public int getCityId() {
    return cityId;
  }

  public String getEmail() {
    return email;
  }

  public LocalDate getJoinDate() {
    return joinDate;
  }
}
