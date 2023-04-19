package com.diyanamancheva.client;

public class ClientRequest {
  private String name;

  public ClientRequest(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
