package com.diyanamancheva.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
  private ClientAccessor clientAccessor;
  private ClientMapper clientMapper;

  @Autowired
  public ClientService(ClientAccessor clientAccessor, ClientMapper clientMapper) {
    this.clientAccessor = clientAccessor;
    this.clientMapper = clientMapper;
  }

   public List<ClientDto> getAllClients() {
    List<Client> clients = clientAccessor.readAllClients();

    return clientMapper.mapClientsToDtos(clients);
  }

    public Client addClient(String name) {
      Client client = new Client(name);
      client = clientAccessor.addClient(client);
      return client;
    }

  public int editClient(int id, String name) {
    Client client = new Client(name);
    client.setId(id);

    return clientAccessor.updateClient(client);
  }

  public int deleteClient(int id) {
    return clientAccessor.deleteClient(id);
  }
  public Client getClientByName(String name, List<Client> clients) {
    Client client = null;
    for (Client clientCurr : clients) {
      if (clientCurr.getName().equals(name)) {
        client = clientCurr;
        break;
      }
    }
    return client;
  }
}
