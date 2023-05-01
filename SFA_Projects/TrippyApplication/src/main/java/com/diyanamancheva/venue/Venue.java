package com.diyanamancheva.venue;

import com.diyanamancheva.city.City;
import com.diyanamancheva.review.Review;
import com.diyanamancheva.type.Type;

import java.util.ArrayList;
import java.util.List;

public class Venue {
  private int id;
  private String name;
  private Type type;
  private City city;
  private String address;
  private float rating;
  private int reviews;

  public Venue(String name, Type type, City city,
               String address, float rating, int reviews){
    this.name = name;
    this.type = type;
    this.city = city;
    this.address = address;
    this.rating = rating;
    this.reviews = reviews;
  }

  public Venue(int id, String name, Type type, City city,
               String address, float rating, int reviews){
    this(name, type, city, address, rating, reviews);
    this.id = id;
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

  public int getReviews() {
    return reviews;
  }

  public void setReviews(int reviews) {
    this.reviews = reviews;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }
}
