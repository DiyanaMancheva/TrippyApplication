package com.diyanamancheva.dto.review;

import com.diyanamancheva.model.User;
import com.diyanamancheva.model.Venue;

import java.time.LocalDate;

public class ReviewByUserDto {
  private int id;
  private String venue;
  private LocalDate creationDate;
  private float rating;
  private String text;

  public ReviewByUserDto(int id, String venue,
                         LocalDate creationDate, float rating, String text){
    this.id = id;
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

  public String getVenue() {
    return venue;
  }

  public void setVenue(String venue) {
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
