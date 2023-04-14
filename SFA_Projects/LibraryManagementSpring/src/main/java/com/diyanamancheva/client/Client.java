package com.diyanamancheva.client;
public class Client {
  private int id;
  private String name;
  public Client(String name){
    this.name = name;
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

  @Override
  public String toString() {
    return "Client: \n" +
           "id = " + id + "\n" +
           "name = " + name + "\n";
  }

}
