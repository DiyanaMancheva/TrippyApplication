package com.diyanamancheva.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "venues")
public class Venue {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="venue_id")
  private int id;
  @Column(name="venue_name")
  private String name;
  @OneToOne
  @JoinColumn(name ="type_id")
  private Type type;
  @OneToOne
  @JoinColumn(name ="city_id")
  private City city;
  @Column(name="address")
  private String address;
  @Column(name="rating")
  private float rating;
  @Column(name="reviewscount")
  private int reviews;

  public Venue() {
  }

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
