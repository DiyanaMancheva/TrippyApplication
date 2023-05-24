package com.diyanamancheva.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "types")
public class Type {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="type_id")
  private int id;
  @Column(name="type_name")
  private String name;

  public Type() {
  }

  public Type(String name){
    this.name = name;
  }

  public Type(int id, String name){
    this(name);
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
}
