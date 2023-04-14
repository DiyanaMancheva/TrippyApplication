package com.diyanamancheva.client;

import com.diyanamancheva.util.ConsoleReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientPresenter {
  private static final String OPTIONS_MESSAGE =
    "------------------------------------------\n" +
    "Clients Menu: \n" +
    "------------------------------------------\n" +
    "1. Search for a client\n" +
    "2. Add a new client\n" +
    "3. Edit a client\n" +
    "4. Remove a client\n" +
    "5. Show all clients\n" +
    "6. Back\n" +
    "------------------------------------------\n" +
    "Your choice: \n" +
    "------------------------------------------";

  private static final String CLIENT_NAME_PROMPT = "Enter client name: ";
  private static final String CLIENT_EDIT_NAME_PROMPT = "Enter new name: ";
  private static final String CLIENT_ID_PROMPT = "Enter client id: ";
  private static final int MIN_MENU_OPTION = 1;
  private static final int MAX_MENU_OPTION = 6;

  private final ClientService clientService;

  @Autowired
  public ClientPresenter(ClientService clientService){
    this.clientService = clientService;
  }
  public void showClientMenu(){
    System.out.println(OPTIONS_MESSAGE);

    int choice = ConsoleReader.readWithinRange(MIN_MENU_OPTION,MAX_MENU_OPTION);

    switch(choice){
      case 1:
        searchClient();
        break;
      case 2:
        addClient();
        break;
      case 3:
        editClient();
        break;
      case 4:
        deleteClient();
        break;
      case 5:
        showAllClients();
        break;
      case 6:
        //showBooksMenu();
        break;
    }
  }

  public void searchClient(){
    System.out.println(CLIENT_NAME_PROMPT);
    String name = ConsoleReader.readString();

    List<Client> clients = clientService.getAllClients();

    Client client = clientService.getClientByName(name, clients);

    System.out.println(client != null ? client : "Client: " + name + " was NOT found.");
  }

  public void addClient(){
    System.out.println(CLIENT_NAME_PROMPT);
    String name = ConsoleReader.readString();

    clientService.addClient(name);
  }

  public void editClient() {
    System.out.print(CLIENT_ID_PROMPT);
    int id = ConsoleReader.readInt();
    System.out.print(CLIENT_EDIT_NAME_PROMPT);
    String name = ConsoleReader.readString();

    int updatedClient = clientService.editClient(name, id);
    if (updatedClient == 1) {
      System.out.println("Client: " + name + " was updated successfully.");
    } else {
      System.out.println("Client: " + name + " was NOT updated.");
    }
  }

  public void deleteClient() {
    System.out.print(CLIENT_ID_PROMPT);
    int id = ConsoleReader.readInt();
    int deletedClient = clientService.deleteClient(id);
    if (deletedClient == 1) {
      System.out.println("Client: " + id + " was deleted successfully");
    } else {
      System.out.println("Client: " + id + " was NOT deleted.");
    }
  }

  public void showAllClients(){
    List<Client> clients = clientService.getAllClients();
    for (Client client : clients) {
      System.out.println(client);
    }
  }
}
