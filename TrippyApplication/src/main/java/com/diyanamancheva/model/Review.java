package com.diyanamancheva.model;

import com.diyanamancheva.model.User;
import com.diyanamancheva.model.Venue;

import java.time.LocalDate;

public class Review {
  private int id;
  private User user;
  private Venue venue;
  private LocalDate creationDate;
  private float rating;
  private String text;

  public Review(User user, Venue venue, LocalDate creationDate,
                float rating, String text){
    this.user = user;
    this.venue = venue;
    this.creationDate = creationDate;
    this.rating = rating;
    this.text = text;
  }

  public Review(int id, User user, Venue venue,
                LocalDate creationDate, float rating, String text){
    this(user, venue, creationDate, rating, text);
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
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
