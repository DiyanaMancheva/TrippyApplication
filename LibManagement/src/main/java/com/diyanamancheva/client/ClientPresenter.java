package com.diyanamancheva.client;

import com.diyanamancheva.LibraryManagement;
import com.diyanamancheva.util.ConsoleReader;

public class ClientPresenter {
  private static final String OPTIONS_MESSAGE =
    "Choose what to do: \n1. Search for a client\n2. Add a new client\n3. " +
    "Edit a client\n4. Remove a client\n5. Show all clients\n6. Exit";
  private static final String CLIENT_NAME_PROMPT = "Enter client name: ";
  private static final String CLIENT_EDIT_NAME_PROMPT = "Enter new name: ";
  private static final int MIN_MENU_OPTION = 1;
  private static final int MAX_MENU_OPTION = 6;

  private static final ClientService clientService = new ClientService();

  private static final LibraryManagement libraryManagement = new LibraryManagement();

  public ClientPresenter(){

  }
  public void showClientMenu(){
    System.out.println(OPTIONS_MESSAGE);

    int choise = ConsoleReader.readWithinRange(MIN_MENU_OPTION,MAX_MENU_OPTION);

    switch(choise){
      case 1:
        searchClient();
        break;
      case 2:
        addClient();
      case 3:
        editClient();
        break;
      case 4:
        deleteClient();
      case 5:
        showAllClients();
        break;
      case 6:
        break;
    }

  }

  public void searchClient(){
    System.out.println(CLIENT_NAME_PROMPT);
    String name = ConsoleReader.readString();

    clientService.searchClient(name);
  }

  public void addClient(){
    System.out.println(CLIENT_NAME_PROMPT);
    String name = ConsoleReader.readString();

    clientService.addClient(name);
  }

  public void editClient(){
    System.out.println(CLIENT_NAME_PROMPT);
    String name = ConsoleReader.readString();
    System.out.println(CLIENT_EDIT_NAME_PROMPT);
    String newName = ConsoleReader.readString();

    clientService.editClient(name, newName);
  }

  public void deleteClient(){
    System.out.println(CLIENT_NAME_PROMPT);
    String name = ConsoleReader.readString();

    clientService.deleteClient(name);
  }

  public void showAllClients(){
    String clientsString = clientService.showAllClients();
    System.out.println(clientsString);
  }
}
