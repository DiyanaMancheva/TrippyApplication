package com.diyanamancheva.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "reviews")
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="review_id")
  private int id;
  @ManyToOne
  @JoinColumn(name ="user_id")
  private User user;
  @OneToOne
  @JoinColumn(name ="venue_id")
  private Venue venue;
  @Column(name="creationdate")
  private LocalDate creationDate;
  @Column(name="rating")
  private float rating;
  @Column(name="text")
  private String text;

  public Review() {
  }

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
