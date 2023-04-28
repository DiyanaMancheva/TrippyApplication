package com.diyanamancheva.review;

import com.diyanamancheva.venue.Venue;

import java.time.LocalDate;

public class ReviewDto {
  private int id;
  private String user;
  private Venue venue;
  private LocalDate creationDate;
  private float rating;
  private String text;

  public ReviewDto(int id, String user, Venue venue,
                   LocalDate creationDate, float rating, String text){
    this.id = id;
    this.user = user;
    this.venue = venue;
    this.creationDate = creationDate;
    this.rating = rating;
    this.text = text;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Venue getVenue() {
    return venue;
  }

  public void setVenue(Venue venue) {
    this.venue = venue;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
