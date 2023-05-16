package com.diyanamancheva.dto.venue;

import com.diyanamancheva.model.City;
import com.diyanamancheva.model.Type;

public class VenueByCityDto {
  private int id;
  private String name;
  private String type;
  private String address;
  private float rating;
  private int reviews;

  public VenueByCityDto(int id, String name, String type, String address,
                        float rating, int reviews){
    this.id = id;
    this.name = name;
    this.type = type;
    this.address = address;
    this.rating = rating;
    this.reviews = reviews;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public int getReviews() {
    return reviews;
  }

  public void setReviews(int reviews) {
    this.reviews = reviews;
  }
}
