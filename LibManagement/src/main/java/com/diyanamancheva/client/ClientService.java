package com.diyanamancheva.client;

import com.diyanamancheva.book.Book;
import com.diyanamancheva.util.ItemAccessor;

import java.util.ArrayList;
import java.util.List;

public class ClientService {
  private static final String ITEMS_FILE_PATH = "src/main/java/com/diyanamancheva/Clients.txt";
  private static final ItemAccessor clientAccessor = new ItemAccessor();
  private static final ClientPresenter clientPresenter = new ClientPresenter();
  private static final ClientMapper clientMapper = new ClientMapper();

  public void searchClient(String name){
    List<Client> clients = getAllClients();
    Client clientToBeSearched = getClientByName(name, clients);

    if(clientToBeSearched == null){
      System.out.println("Client: " + name + " not found.");
      clientPresenter.showClientMenu();
    }

    StringBuilder printClient = new StringBuilder();
    printClient.append("Name: " + clientToBeSearched.getName() + "\nBooks:\n");
    if(clientToBeSearched.getBooks().size() == 0){
      printClient.append("No books");
    }
    else{
      ArrayList<Book> books = clientToBeSearched.getBooks();
      int count = 0;
      for (Book book: books) {
        count++;
        printClient.append(count + ". " + book.getName()).append("\n");
      }
    }

    System.out.println(printClient);
    clientPresenter.showClientMenu();
  }

  public void addClient(String name) {
    List<Client>clients = getAllClients();
    Client client = getClientByName(name, clients);

    if(client != null){
      System.out.println("The client already exists!");
      clientPresenter.showClientMenu();
    }

    int id = clientAccessor.readAllItems(ITEMS_FILE_PATH).size() + 1;
    client = new Client(name);

    client.setId(id);

    String clientString = clientMapper.mapClientToString(client);
    clientAccessor.readItem(clientString, ITEMS_FILE_PATH);
    System.out.println("Client: " + name + " was successfully added.");
    clientPresenter.showClientMenu();
  }

  public void editClient(String name, String newName){
    List<Client> clients = getAllClients();

    try{
      Client clientToBeEdited = getClientByName(name, clients);
      clientToBeEdited.setName(newName);
      String clientsString = clientMapper.mapClientListToString(clients);
      clientAccessor.overwriteFile(clientsString, ITEMS_FILE_PATH);
      System.out.println("Client: " + newName + " was successfully edited.");
      clientPresenter.showClientMenu();
    }
    catch (NullPointerException e){
      System.out.println("Client with such name was NOT found! Please try again!");
      clientPresenter.editClient();
    }
  }

  public void deleteClient(String name) {
    List<Client> clients = getAllClients();
    Client clientToBeDeleted = getClientByName(name, clients);

    if(clientToBeDeleted.getBooks().size() != 0){
      System.out.println("The client has books to return.");
      clientPresenter.showClientMenu();
    }

    try{
      clients.remove(clientToBeDeleted);

      for (int i = clientToBeDeleted.getId()-1; i < clients.size(); i++) {
        clients.get(i).setId(i + 1);
      }

      String clientsString = clientMapper.mapClientListToString(clients);
      clientAccessor.overwriteFile(clientsString, ITEMS_FILE_PATH);
      System.out.println("Client: " + name + " was successfully deleted.");
      clientPresenter.showClientMenu();
    }
    catch (NullPointerException e){
      System.out.println("Client with such name was NOT found!");
      clientPresenter.showClientMenu();
    }
   }

  public String showAllClients(){
    List<Client>clients = getAllClients();
    return clientMapper.mapClientListToString(clients);
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

  public List<Client> getAllClients() {
    List<String> clientsString = clientAccessor.readAllItems(ITEMS_FILE_PATH);
    ArrayList<Client> clients = new ArrayList<Client>();
    for (String clientString : clientsString) {
      Client client = clientMapper.mapStringToClient(clientString);
      clients.add(client);
    }

    return clients;
  }
}
