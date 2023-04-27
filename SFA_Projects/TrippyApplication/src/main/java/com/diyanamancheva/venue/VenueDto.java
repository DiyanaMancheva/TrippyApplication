package com.diyanamancheva.venue;

import com.diyanamancheva.city.City;
import com.diyanamancheva.review.Review;
import com.diyanamancheva.type.Type;

import java.util.ArrayList;
import java.util.List;

public class VenueDto {
  private int id;
  private String name;
  private Type type;
  private City city;
  private String address;
  private float rating;
  private List<Review> reviews;

  public VenueDto(int id, String name, Type type, City city,
               String address, float rating){
    this.id = id;
    this.name = name;
    this.type = type;
    this.city = city;
    this.address = address;
    this.rating = 0.0f;
    this.reviews = new ArrayList<>();
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
    return this.calculateAverageRating();
  }

  private float calculateAverageRating() {
    Float ratingAll = 0.0f;
    for (Review review : this.reviews){
      ratingAll += review.getRating();
    }
    Float ratingAverage = ratingAll / this.reviews.size();
    return ratingAverage;
  }
}