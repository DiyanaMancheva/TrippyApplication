package com.diyanamancheva.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="user_id")
  private int id;
  @Column(name="username")
  private String username;

  @ManyToOne
  @JoinColumn(name ="city_id")
  //@Column(name="city_id")
  private City city;
  @Column(name="email")
  private String email;
  @Column(name="joindate")
  private LocalDate joinDate;
  @OneToMany
  @JoinColumn(name ="review_id")
  private List<Review> reviews;

  public User() {
  }

  public User(String username, City city,
              String email, LocalDate joinDate){
    this.username = username;
    this.city = city;
    this.email = email;
    this.joinDate = joinDate;
    this.reviews = new ArrayList<>();
  }

  public User(int id, String username, City city,
              String email, LocalDate joinDate){
    this(username, city, email, joinDate);
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getJoinDate() {
    return joinDate;
  }

  public void setJoinDate(LocalDate joinDate) {
    this.joinDate = joinDate;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }
}
