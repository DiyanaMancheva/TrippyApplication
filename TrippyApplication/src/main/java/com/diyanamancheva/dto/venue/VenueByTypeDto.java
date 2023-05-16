package com.diyanamancheva.dto.venue;

public class VenueByTypeDto {
  private int id;
  private String name;
  private String city;
  private String address;
  private float rating;
  private int reviews;

  public VenueByTypeDto(int id, String name, String city,
                        String address, float rating, int reviews){
    this.id = id;
    this.name = name;
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

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
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
