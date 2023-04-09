package com.diyanamancheva.order;

import com.diyanamancheva.author.Author;
import com.diyanamancheva.author.AuthorMapper;
import com.diyanamancheva.book.BookService;
import com.diyanamancheva.client.Client;
import com.diyanamancheva.client.ClientPresenter;
import com.diyanamancheva.client.ClientService;
import com.diyanamancheva.book.Book;
import com.diyanamancheva.book.BookMapper;
import com.diyanamancheva.book.BookPresenter;
import com.diyanamancheva.util.ItemAccessor;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
  private static final String ORDERS_FILE_PATH = "src/main/java/com/diyanamancheva/Orders.txt";
  private static final String AUTHORS_FILE_PATH = "src/main/java/com/diyanamancheva/Authors.txt";
  private static final ItemAccessor orderAccessor = new ItemAccessor();
  private static final BookPresenter bookPresenter = new BookPresenter();
  private static final ClientPresenter clientPresenter = new ClientPresenter();
  private static final OrderPresenter orderPresenter = new OrderPresenter();
  private static final OrderMapper orderMapper = new OrderMapper();
  private static final ClientService clientService = new ClientService();
  private static final BookService bookService = new BookService();
  private static final AuthorMapper authorMapper = new AuthorMapper();

  public void searchOrderByClient(String clientName){
    List<Order> orders = getAllOrders();
    ArrayList<Order> ordersByClient = new ArrayList<Order>();

    if (orders.size() == 0){
      System.out.println("Order list is empty.");
      orderPresenter.showOrderMenu();
    }

    for (Order orderFromList: orders) {
      if (orderFromList.getClient().getName().equals(clientName)){
        ordersByClient.add(orderFromList);
      }
    }

    if(ordersByClient.size() == 0){
      System.out.println("No orders found for this client.");
      orderPresenter.showOrderMenu();
    }

    String ordersByClientString = orderMapper.mapOrderListToString(ordersByClient);

    System.out.println(ordersByClientString);
    orderPresenter.showOrderMenu();
  }

  public void searchOrderByIssueDate(String issueDate, String onBeforeAfter){
    List<Order> orders = getAllOrders();
    ArrayList<Order> ordersByIssueDate = new ArrayList<>();

    if (orders.size() == 0){
      System.out.println("Order list is empty.");
      orderPresenter.showOrderMenu();
    }

    String[] issueDateTokens = issueDate.split("/");
    Integer dayInt = Integer.parseInt(issueDateTokens[0]);
    Integer monthInt = Integer.parseInt(issueDateTokens[1]);
    Integer yearInt = Integer.parseInt(issueDateTokens[2]);

    switch (onBeforeAfter){
      case "on":
        for (Order order: orders) {
          if (order.getIssueDate().equals(issueDate)){
            ordersByIssueDate.add(order);
          }
        }
        break;
      case "after":
        for (Order order: orders) {
          String[] orderIssueDateTokens = order.getIssueDate().split("/");
          Integer orderDayInt = Integer.parseInt(orderIssueDateTokens[0]);
          Integer orderMonthInt = Integer.parseInt(orderIssueDateTokens[1]);
          Integer orderYearInt = Integer.parseInt(orderIssueDateTokens[2]);

          if (orderYearInt > yearInt){
            ordersByIssueDate.add(order);
          }else if(orderYearInt >= yearInt && orderMonthInt > monthInt){
            ordersByIssueDate.add(order);
          }else if(orderYearInt >= yearInt && orderMonthInt >= monthInt && orderDayInt > dayInt){
            ordersByIssueDate.add(order);
          }
        }
        break;
      case "before":
        for (Order order: orders) {
          String[] orderIssueDateTokens = order.getIssueDate().split("/");
          Integer orderDayInt = Integer.parseInt(orderIssueDateTokens[0]);
          Integer orderMonthInt = Integer.parseInt(orderIssueDateTokens[1]);
          Integer orderYearInt = Integer.parseInt(orderIssueDateTokens[2]);

          if (orderYearInt < yearInt){
            ordersByIssueDate.add(order);
          }else if(orderYearInt <= yearInt && orderMonthInt < monthInt){
            ordersByIssueDate.add(order);
          }else if(orderYearInt <= yearInt && orderMonthInt < monthInt && orderDayInt < dayInt){
            ordersByIssueDate.add(order);
          }
        }
        break;
    }

    String ordersByIssueDateString = orderMapper.mapOrderListToString(ordersByIssueDate);

    System.out.println(ordersByIssueDateString);
    orderPresenter.showOrderMenu();
  }

  public void createOrder(String bookName, String clientName, String issueDate) {
    List<Book> books = bookService.getAllBooks();
    Book book = bookService.getBookByName(bookName, books);

    if(book == null){
      System.out.println("Can not find the book!");
      bookPresenter.showBookMenu();
    }
    if (!book.isAvailable){
      System.out.println("The book is not available.");
      orderPresenter.showOrderMenu();
    }

    List<Client> clients = clientService.getAllClients();
    Client client = clientService.getClientByName(clientName, clients);

    if (client == null){
      System.out.println("Can not find the client!");
      clientPresenter.showClientMenu();
    }

    Order order = new Order(book, client, issueDate);


    int orderId = orderAccessor.readAllItems(ORDERS_FILE_PATH).size() + 1;
    order.setId(orderId);

    String orderString = orderMapper.mapOrderToString(order);
    orderAccessor.readItem(orderString, ORDERS_FILE_PATH);

    System.out.println("Created order:\nClient: " + order.getClient().getName() + "\nBook: " + order.getBook().getName());
    orderPresenter.showOrderMenu();
  }

  public void deleteOrder(String id) {
    List<Order> orders = getAllOrders();
    Order orderToBeDeleted = getOrderById(id, orders);

    if(orderToBeDeleted == null){
      System.out.println("The order was not found.");
      orderPresenter.showOrderMenu();
    }

      orders.remove(orderToBeDeleted);

      for (int i = orderToBeDeleted.getId()-1; i < orders.size(); i++) {
        orders.get(i).setId(i + 1);
      }

      String ordersString = orderMapper.mapOrderListToString(orders);
      orderAccessor.overwriteFile(ordersString, ORDERS_FILE_PATH);
      System.out.println("Order: " + id + " was successfully deleted.");
      orderPresenter.showOrderMenu();
    }


  public String showAllOrders(){
    List<Order> orders = getAllOrders();
    return orderMapper.mapOrderListToString(orders);
  }

  private Order getOrderById(String id, List<Order> orders) {
    Order order = null;
    for (Order orderFromList : orders) {
      Integer idInt = Integer.parseInt(id);
      if (orderFromList.getId() == idInt) {
        order = orderFromList;
        break;
      }
    }
    return order;
  }

  private List<Order> getAllOrders() {
    List<String> ordersString = orderAccessor.readAllItems(ORDERS_FILE_PATH);
    ArrayList<Order> orders = new ArrayList<Order>();
    for (String orderString : ordersString) {
      Order order = orderMapper.mapStringToOrder(orderString);
      orders.add(order);
    }

    return orders;
  }
}
