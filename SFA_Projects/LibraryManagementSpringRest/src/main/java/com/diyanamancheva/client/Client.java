package com.diyanamancheva.client;

public class Client {
  private int id;
  private String name;
  public Client(String name){
    this.name = name;
  }
  public Client(int id, String name){
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
