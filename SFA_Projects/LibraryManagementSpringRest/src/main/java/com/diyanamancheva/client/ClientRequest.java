package com.diyanamancheva.client;

import javax.validation.constraints.Pattern;

public class ClientRequest {
  @Pattern(regexp = "[A-Z]+[a-z]+[\\s.][A-Z]+[a-z]+", message = "Name must not be null or contain numbers")
  private String name;
  public ClientRequest(){}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
