package com.diyanamancheva.user;

import com.diyanamancheva.city.City;
import com.diyanamancheva.review.Review;
import com.diyanamancheva.type.Type;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDto {
  private int id;
  private String username;
  private City city;
  private String email;
  private LocalDate joinDate;

  public UserDto(int id, String username, City city,
                 String email, LocalDate joinDate){
    this.id = id;
    this.username = username;
    this.city = city;
    this.email = email;
    this.joinDate = joinDate;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getJoinDate() {
    return joinDate;
  }

  public void setJoinDate(LocalDate joinDate) {
    this.joinDate = joinDate;
  }
}
