package com.diyanamancheva.dto.venue;

import com.diyanamancheva.model.City;
import com.diyanamancheva.model.Review;
import com.diyanamancheva.model.Type;

import java.util.ArrayList;
import java.util.List;

public class VenueDto {
  private int id;
  private String name;
  private Type type;
  private City city;
  private String address;
  private float rating;
  private int reviews;

  public VenueDto(int id, String name, Type type, City city,
               String address, float rating, int reviews){
    this.id = id;
    this.name = name;
    this.type = type;
    this.city = city;
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

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
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
