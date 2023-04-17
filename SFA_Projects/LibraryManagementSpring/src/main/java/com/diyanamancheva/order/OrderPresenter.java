package com.diyanamancheva.order;

import com.diyanamancheva.util.ConsoleReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class OrderPresenter {

  private static final String OPTIONS_MESSAGE =
    "------------------------------------------\n" +
    "Orders Menu: \n" +
    "------------------------------------------\n" +
    "1. Search for an order by Id\n" +
    "2. Search for an order by clientId\n" +
    "3. Search for an order by bookId\n" +
    "4. Search for an order created on\n" +
    "5. Search for an order created before\n" +
    "6. Search for an order created after\n" +
    "7. Search for an order with due date\n" +
    "8. Search for an order with due date before\n" +
    "9. Search for an order with due date after\n" +
    "10. Add a new order\n" +
    "11. Edit an order\n" +
    "12. Delete an order\n" +
    "13. Show all orders\n" +
    "14. Exit\n" +
    "------------------------------------------\n" +
    "Your choice: \n" +
    "------------------------------------------";

  private static final String ORDER_ID_PROMPT = "Enter order id: ";
  private static final String BOOK_ID_PROMPT = "Enter book id: ";
  private static final String CLIENT_ID_PROMPT = "Enter client id: ";
  private static final String ORDER_ISSUEDATE_PROMPT = "Enter issue date: ";
  private static final String ORDER_DUEDATE_PROMPT = "Enter due date: ";

  private static final int MIN_MENU_OPTION = 1;
  private static final int MAX_MENU_OPTION = 14;

  private final OrderService orderService;

  @Autowired
  public OrderPresenter(OrderService orderService) {
    this.orderService = orderService;
  }

  public void showOrderMenu() {
    System.out.println(OPTIONS_MESSAGE);

    int choice = ConsoleReader.readWithinRange(MIN_MENU_OPTION, MAX_MENU_OPTION);

    switch (choice) {
      case 1:
        searchOrderById();
        break;
      case 2:
        searchOrderByClientId();
        break;
      case 3:
        searchOrderByBookId();
        break;
      case 4:
        searchOrderOnIssueDate();
        break;
      case 5:
        searchOrderBeforeIssueDate();
        break;
      case 6:
        searchOrderAfterIssueDate();
        break;
      case 7:
        searchOrderOnDueDate();
        break;
      case 8:
        searchOrderBeforeDueDate();
        break;
      case 9:
        searchOrderAfterDueDate();
        break;
      case 10:
        addOrder();
        break;
      case 11:
        editOrder();
        break;
      case 12:
        deleteOrder();
        break;
      case 13:
        showAllOrders();
        break;
      case 14:
        break;
    }
  }

  public void searchOrderById() {
    System.out.println(ORDER_ID_PROMPT);
    int id = ConsoleReader.readInt();

    List<Order> orders = orderService.getAllOrders();

    Order order = orderService.getOrderById(id, orders);

    System.out.println("------------------------------------------");
    System.out.println(order != null ? order : "Order: " + id + " was NOT found.");
    System.out.println("------------------------------------------");
  }

  public void searchOrderByClientId() {
    System.out.println(CLIENT_ID_PROMPT);
    int clientId = ConsoleReader.readInt();

    List<Order> orders = orderService.getAllOrders();
    List<Order> ordersByClientId = orderService.getOrderByClientId(clientId, orders);

    if (ordersByClientId.size() != 0) {
      for (Order order : ordersByClientId) {
        System.out.println("------------------------------------------");
        System.out.println(order);
        System.out.println("------------------------------------------");
      }
    } else {
      System.out.println("------------------------------------------");
      System.out.println("Orders for clientId: " + clientId + " were NOT found.");
      System.out.println("------------------------------------------");
    }
  }

  public void searchOrderByBookId() {
    System.out.println(BOOK_ID_PROMPT);
    int bookId = ConsoleReader.readInt();

    List<Order> orders = orderService.getAllOrders();
    List<Order> ordersByBookId = orderService.getOrderByBookId(bookId, orders);

    if (ordersByBookId.size() != 0) {
      for (Order order : ordersByBookId) {
        System.out.println("------------------------------------------");
        System.out.println(order);
        System.out.println("------------------------------------------");
      }
    } else {
      System.out.println("------------------------------------------");
      System.out.println("Orders for bookId: " + bookId + " were NOT found.");
      System.out.println("------------------------------------------");
    }
  }

  public void searchOrderOnIssueDate() {
    System.out.println(ORDER_ISSUEDATE_PROMPT);
    String issueDate = ConsoleReader.readString();
    LocalDate issueDateLocal = LocalDate.parse(issueDate);

    List<Order> orders = orderService.getAllOrders();
    List<Order> ordersOnIssueDate = orderService.getOrderOnIssueDate(issueDateLocal, orders);

    if (ordersOnIssueDate.size() != 0) {
      for (Order order : ordersOnIssueDate) {
        System.out.println("------------------------------------------");
        System.out.println(order);
        System.out.println("------------------------------------------");
      }
    } else {
      System.out.println("------------------------------------------");
      System.out.println("Orders created on date " + issueDate + " were NOT found.");
      System.out.println("------------------------------------------");
    }
  }

  public void searchOrderBeforeIssueDate() {
    System.out.println(ORDER_ISSUEDATE_PROMPT);
    String issueDate = ConsoleReader.readString();
    LocalDate issueDateLocal = LocalDate.parse(issueDate);

    List<Order> orders = orderService.getAllOrders();
    List<Order> ordersBeforeIssueDate = orderService.getOrderBeforeIssueDate(issueDateLocal, orders);

    if (ordersBeforeIssueDate.size() != 0) {
      for (Order order : ordersBeforeIssueDate) {
        System.out.println("------------------------------------------");
        System.out.println(order);
        System.out.println("------------------------------------------");
      }
    } else {
      System.out.println("------------------------------------------");
      System.out.println("Orders created before date " + issueDate + " were NOT found.");
      System.out.println("------------------------------------------");
    }
  }

  public void searchOrderAfterIssueDate(){
    System.out.println(ORDER_ISSUEDATE_PROMPT);
    String issueDate = ConsoleReader.readString();
    LocalDate issueDateLocal = LocalDate.parse(issueDate);

    List<Order> orders = orderService.getAllOrders();
    List<Order> ordersAfterIssueDate = orderService.getOrderAfterIssueDate(issueDateLocal, orders);

    if (ordersAfterIssueDate.size() != 0) {
      for (Order order : ordersAfterIssueDate) {
        System.out.println("------------------------------------------");
        System.out.println(order);
        System.out.println("------------------------------------------");
      }
    } else {
      System.out.println("------------------------------------------");
      System.out.println("Orders created after date " + issueDate + " were NOT found.");
      System.out.println("------------------------------------------");
    }
  }

  public void searchOrderOnDueDate(){
    System.out.println(ORDER_DUEDATE_PROMPT);
    String dueDate = ConsoleReader.readString();
    LocalDate dueDateLocal = LocalDate.parse(dueDate);

    List<Order> orders = orderService.getAllOrders();
    List<Order> ordersOnDueDate = orderService.getOrderOnDueDate(dueDateLocal, orders);

    if (ordersOnDueDate.size() != 0) {
      for (Order order : ordersOnDueDate) {
        System.out.println("------------------------------------------");
        System.out.println(order);
        System.out.println("------------------------------------------");
      }
    } else {
      System.out.println("------------------------------------------");
      System.out.println("Orders with due date on date " + dueDate + " were NOT found.");
      System.out.println("------------------------------------------");
    }
  }

  public void searchOrderBeforeDueDate(){
    System.out.println(ORDER_DUEDATE_PROMPT);
    String dueDate = ConsoleReader.readString();
    LocalDate dueDateLocal = LocalDate.parse(dueDate);

    List<Order> orders = orderService.getAllOrders();
    List<Order> ordersBeforeDueDate = orderService.getOrderBeforeDueDate(dueDateLocal, orders);

    if (ordersBeforeDueDate.size() != 0) {
      for (Order order : ordersBeforeDueDate) {
        System.out.println("------------------------------------------");
        System.out.println(order);
        System.out.println("------------------------------------------");
      }
    } else {
      System.out.println("------------------------------------------");
      System.out.println("Orders with due date before date " + dueDate + " were NOT found.");
      System.out.println("------------------------------------------");
    }
  }

  public void searchOrderAfterDueDate(){
    System.out.println(ORDER_DUEDATE_PROMPT);
    String dueDate = ConsoleReader.readString();
    LocalDate dueDateLocal = LocalDate.parse(dueDate);

    List<Order> orders = orderService.getAllOrders();
    List<Order> ordersAfterDueDate = orderService.getOrderAfterDueDate(dueDateLocal, orders);

    if (ordersAfterDueDate.size() != 0) {
      for (Order order : ordersAfterDueDate) {
        System.out.println("------------------------------------------");
        System.out.println(order);
        System.out.println("------------------------------------------");
      }
    } else {
      System.out.println("------------------------------------------");
      System.out.println("Orders with due date after date " + dueDate + " were NOT found.");
      System.out.println("------------------------------------------");
    }
  }

  public void addOrder(){
    System.out.println(CLIENT_ID_PROMPT);
    int clientId = ConsoleReader.readInt();
    System.out.println(BOOK_ID_PROMPT);
    int bookId = ConsoleReader.readInt();
    System.out.println(ORDER_ISSUEDATE_PROMPT);
    String issueDate = ConsoleReader.readString();
    LocalDate issueDateLocal = LocalDate.parse(issueDate);

    orderService.addOrder(clientId, bookId, issueDateLocal);
  }

  public void editOrder() {
    System.out.print(ORDER_ID_PROMPT);
    int id = ConsoleReader.readInt();
    System.out.print(ORDER_DUEDATE_PROMPT);
    String dueDate = ConsoleReader.readString();
    LocalDate dueDateLocal = LocalDate.parse(dueDate);

    int updatedOrder = orderService.editOrder(dueDateLocal, id);
    if (updatedOrder == 1) {
      System.out.println("------------------------------------------");
      System.out.println("Order: " + id + " was updated successfully.");
      System.out.println("------------------------------------------");
    } else {
      System.out.println("------------------------------------------");
      System.out.println("Order: " + id + " was NOT updated.");
      System.out.println("------------------------------------------");
    }
  }

  public void deleteOrder() {
    System.out.print(ORDER_ID_PROMPT);
    int id = ConsoleReader.readInt();
    int deletedOrder = orderService.deleteOrder(id);
    if (deletedOrder == 1) {
      System.out.println("------------------------------------------");
      System.out.println("Order: " + id + " was deleted successfully");
      System.out.println("------------------------------------------");
    } else {
      System.out.println("------------------------------------------");
      System.out.println("Order: " + id + " was NOT deleted.");
      System.out.println("------------------------------------------");
    }
  }

  public void showAllOrders(){
    List<Order> orders = orderService.getAllOrders();
    for (Order order : orders) {
      System.out.println("------------------------------------------");
      System.out.println(order);
      System.out.println("------------------------------------------");
    }
  }
}
