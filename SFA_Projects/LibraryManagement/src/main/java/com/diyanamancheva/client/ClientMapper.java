package com.diyanamancheva.client;

import java.util.List;

public class ClientMapper {
  public Client mapStringToClient(String string) {
    String[] tokens = string.split(" ");
    int id = Integer.parseInt(tokens[0]);
    StringBuilder nameBuilder = new StringBuilder();
    for (int i = 1; i < tokens.length; i++) {
      if (i < tokens.length - 1) {
        nameBuilder.append(tokens[i]).append(" ");
      } else {
        nameBuilder.append(tokens[i]);
      }
    }
    String name = nameBuilder.toString();

    Client client = new Client(name);
    client.setId(id);

    return client;
  }

  public String mapClientToString(Client client){
    int idtString = client.getId();
    String nameString = client.getName();

    return String.join(" ", Integer.toString(idtString), nameString);
  }

  public String mapClientListToString(List<Client> clientList){
    StringBuilder clientStringBuilder = new StringBuilder();
    for (Client client: clientList) {
      String clientString = mapClientToString(client);
      clientStringBuilder.append(clientString).append("\n");
    }

    return clientStringBuilder.toString();
  }
}
