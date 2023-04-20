package com.diyanamancheva.client;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClientMapper{
  public List<Client> mapResultSetToClients(ResultSet clientsResultSet) {
    List<Client> clientsList = new ArrayList<>();
    try (clientsResultSet) {
      while (clientsResultSet.next()) {
        int id = clientsResultSet.getInt(1);
        String name = clientsResultSet.getString(2);
        Client client = new Client(id, name);
        clientsList.add(client);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return clientsList;
  }

  public List<ClientDto> mapClientsToDtos(List<Client> clients){
    ArrayList<ClientDto> clientDtos = new ArrayList<>();

    for(Client client : clients){
      ClientDto clientDto = new ClientDto(client.getName());
      clientDtos.add(clientDto);
    }
    return clientDtos;
  }
}
