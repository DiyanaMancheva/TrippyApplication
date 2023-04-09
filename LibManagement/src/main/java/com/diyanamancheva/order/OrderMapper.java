package com.diyanamancheva.order;

import com.diyanamancheva.author.Author;
import com.diyanamancheva.book.Book;
import com.diyanamancheva.book.BookService;
import com.diyanamancheva.client.Client;
import com.diyanamancheva.client.ClientService;

import java.util.List;

public class OrderMapper {

  private static final ClientService clientService = new ClientService();
  private static final BookService bookService = new BookService();
  public Order mapStringToOrder(String string) {
    String[] parts = string.split("_");
    String clientPart = parts[0];
    String bookPart = parts[1];
    String issueDatePart = parts[2];

    String[] clientTokens = clientPart.split(" ");

    int orderId = Integer.parseInt(clientTokens[0]);

    StringBuilder clientNameBuilder = new StringBuilder();

    for (int i = 1; i < clientTokens.length; i++) {
      if (i < clientTokens.length - 1) {
        clientNameBuilder.append(clientTokens[i]).append(" ");
      } else {
        clientNameBuilder.append(clientTokens[i]);
      }
    }
    String clientName = clientNameBuilder.toString();

    List<Client> clients = clientService.getAllClients();
    Client client = clientService.getClientByName(clientName, clients);

    List<Book> books = bookService.getAllBooks();
    Book book = bookService.getBookByName(bookPart, books);

    Order order = new Order(book, client, issueDatePart);
    order.setId(orderId);

    return order;
  }

  public String mapOrderToString(Order order){
    int idString = order.getId();
    String bookNameString = order.getBook().getName();
    String clientNameString = order.getClient().getName();
    String issueDate = order.getIssueDate();
    String dueDate = order.getDueDate();

    String idAndClientNameString = String.join(" ", Integer.toString(idString), clientNameString);
    return String.join("_", idAndClientNameString, bookNameString, issueDate, dueDate);
  }

  public String mapOrderListToString(List<Order> orderList){
    StringBuilder orderStringBuilder = new StringBuilder();
    for (Order order: orderList) {
      String orderString = mapOrderToString(order);
      orderStringBuilder.append(orderString).append("\n");
    }

    return orderStringBuilder.toString();
  }
}
