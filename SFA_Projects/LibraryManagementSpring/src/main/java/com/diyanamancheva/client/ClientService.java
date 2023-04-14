package com.diyanamancheva.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
  private final ClientAccessor clientAccessor;

  @Autowired
  public ClientService(ClientAccessor clientAccessor) {
    this.clientAccessor = clientAccessor;
  }

  public List<Client> getAllClients() {
    return clientAccessor.readAllClients();
  }
  public void addClient(String name) {
    Client client = new Client(name);
    clientAccessor.addClient(client);
  }

  public int editClient(String name, int id) {
    Client client = new Client(name);
    client.setId(id);
    return clientAccessor.updateClient(client);
  }

  public int deleteClient(int id) {
    return clientAccessor.deleteClient(id);
  }

  public Client getClientByName(String name, List<Client> clients) {
    Client client = null;
    for (Client clientFromList : clients) {
      if (clientFromList.getName().equals(name)) {
        client = clientFromList;
        break;
      }
    }
    return client;
  }
}
