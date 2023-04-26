package com.diyanamancheva.user;

import com.diyanamancheva.city.City;

import java.time.LocalDate;

public class User {
  private int id;
  private String username;
  private City city;
  private String email;
  private LocalDate joinDate;

  public User(String username, City city,
              String email, LocalDate joinDate){
    this.username = username;
    this.city = city;
    this.email = email;
    this.joinDate = joinDate;
  }

  public User(int id, String username, City city,
              String email, LocalDate joinDate){
    this(username, city, email, joinDate);
    this.id = id;
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
