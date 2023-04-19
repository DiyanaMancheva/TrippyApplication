package com.diyanamancheva.client;

public class ClientDto {
  private String name;

  public ClientDto(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
