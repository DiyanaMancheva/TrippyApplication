package com.diyanamancheva.author;

public class Author {
  private int id;
  private String name;

  public Author(String name){
    this.name = name;
  }

  public int getId() { return id; }

  public void setId(int id) { this.id = id;}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Author: \n" +
           "id = " + id + "\n" +
           "name = " + name + "\n";
  }
}
