package com.diyanamancheva.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Pattern;

public class UserUpdateRequest {
  @NotNull(message = "Address must not be null")
  @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$",
           message = "Username can contain only lower and capital letters, digits, and dot/underscore.")
  private String username;
  @Positive(message = "CityId must be greater than 0")
  private int city;
  @Pattern(regexp =  "^[A-Za-z0-9+_.-]+@(.+)$",
           message = "Email can contain only lower and capital letters, digits, and plus/minus/dot/underscore.")
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
