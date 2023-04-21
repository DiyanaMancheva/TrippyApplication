package com.diyanamancheva.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ClientController {
  private ClientService clientService;

  @Autowired
  public ClientController(ClientService clientService){
    this.clientService = clientService;
  }

  @GetMapping("/clients")
  public ResponseEntity<List<ClientDto>> printAllClients() {
    List<ClientDto> clients = clientService.getAllClients();
    return ResponseEntity.ok(clients);
  }

  @GetMapping("/clients/{id}")
  public ResponseEntity<ClientDto> getClient(@PathVariable int id) {
    Client client = clientService.getClientById(id);
    ClientDto clientDto = new ClientDto(client.getName());
    return ResponseEntity.ok(clientDto);
  }

  @PostMapping("/clients")
  public ResponseEntity<Void> createClient(@RequestBody ClientRequest clientRequest){
    Client client = clientService.addClient(clientRequest.getName());
    URI location = UriComponentsBuilder.fromUriString("/clients/{id}")
                                       .buildAndExpand(client.getId())
                                       .toUri();
    return ResponseEntity.created(location).build();
  }

  @PutMapping("/clients/{id}")
  public ResponseEntity<ClientDto> updateClient(
    @RequestBody ClientRequest clientRequest, @PathVariable int id,
    @RequestParam(required = false) boolean returnOld) {

    ClientDto clientDto = clientService.editClient(id, clientRequest);
    if (returnOld) {
      return ResponseEntity.ok(clientDto);
    } else {
      return ResponseEntity.noContent().build();
    }
  }
}
