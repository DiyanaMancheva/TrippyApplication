package com.diyanamancheva.client;

import com.diyanamancheva.exception.DatabaseConnectivityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClientMapper{
  private static final Logger log = LoggerFactory.getLogger(ClientAccessor.class);

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
        log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
        throw new DatabaseConnectivityException(e);
    }
    return clientsList;
  }

  public List<ClientDto> mapClientsToDtos(List<Client> clients){
    ArrayList<ClientDto> clientDtos = new ArrayList<>();

    for(Client client : clients){
      ClientDto clientDto = new ClientDto(client.getId(), client.getName());
      clientDtos.add(clientDto);
    }
    return clientDtos;
  }
}
