package com.diyanamancheva.controller.request.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class UserRequest {
  @NotNull(message = "Address must not be null")
  @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$",
           message = "Username can contain only lower and capital letters, digits, and dot/underscore.")
  private String username;
  @Positive(message = "CityId must be greater than 0")
  private int cityId;
  @Pattern(regexp =  "^[A-Za-z0-9+_.-]+@(.+)$",
           message = "Email can contain only lower and capital letters, digits, and plus/minus/dot/underscore.")
  private String email;
  //@Pattern(regexp = "^(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$",
  //         message = "Date must be in yyyy-mm-dd format.")
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
