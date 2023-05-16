package com.diyanamancheva.controller.request.review;

import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class ReviewRequest {
  @Positive(message = "UserId must be greater than 0")
  private int userId;
  @Positive(message = "VenueId must be greater than 0")
  private int venueId;
  private LocalDate creationDate;
  //@Pattern(regexp = "[1-5]([.][1-9]*)?", message = "Rating must be between 1 and 5")
  private float rating;
  private String text;

  public ReviewRequest(){}

  public ReviewRequest(int userId, int venueId, LocalDate creationDate,
                       float rating, String text){
    this.userId = userId;
    this.venueId = venueId;
    this.creationDate = creationDate;
    this.rating = rating;
    this.text = text;
  }

  public int getUserId() {
    return userId;
  }

  public int getVenueId() {
    return venueId;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public float getRating() {
    return rating;
  }

  public String getText() {
    return text;
  }
}
