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

  public Client getClientById(int id) {
    return clientAccessor.readClientById(id);
  }

  public Client addClient(String name) {
    Client client = new Client(name);
    client = clientAccessor.addClient(client);
    return client;
  }

  public ClientDto editClient(int id, ClientRequest clientRequest) {

    Client client = getClientById(id);
    Client clientNew = new Client(id, clientRequest.getName());
    clientAccessor.updateClient(clientNew);
    ClientDto clientDto = new ClientDto(client.getName());

    return clientDto;
  }
}
