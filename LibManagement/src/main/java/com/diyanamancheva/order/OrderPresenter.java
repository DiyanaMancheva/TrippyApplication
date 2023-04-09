package com.diyanamancheva.order;

import com.diyanamancheva.LibraryManagement;
import com.diyanamancheva.order.OrderService;
import com.diyanamancheva.util.ConsoleReader;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderPresenter {
  private static final String OPTIONS_MESSAGE =
    "Choose what to do: \n1. Search for an order by client\n2. Search for an order by issue date\n3. " +
    "Show all orders\n4. Create a new order\n5. Remove an order\n6. Exit";
  private static final String ORDER_ID_PROMPT = "Enter order id: ";
  private static final String BOOK_NAME_PROMPT = "Enter book name: ";
  private static final String CLIENT_NAME_PROMPT = "Enter client name: ";
  private static final String ISSUEDATE_PROMPT = "Enter issue date: ";
  private static final String ISSUEDATE_ON_BEFORE_AFTER_PROMPT = "Enter particular time(on/before/after): ";
  private static final String DUEDATE_PROMPT = "Enter due date: ";
  private static final int MIN_MENU_OPTION = 1;
  private static final int MAX_MENU_OPTION = 6;

  private static final OrderService orderService = new OrderService();

  private static final LibraryManagement libraryManagement = new LibraryManagement();

  public OrderPresenter(){

  }
  public void showOrderMenu(){
    System.out.println(OPTIONS_MESSAGE);

    int choice = ConsoleReader.readWithinRange(MIN_MENU_OPTION,MAX_MENU_OPTION);

    switch(choice){
      case 1:
        searchOrderByClient();
        break;
      case 2:
        searchOrderByIssueDate();
        break;
      case 3:
         showAllOrders();
        break;
      case 4:
        createOrder();
        break;
      case 5:
        deleteOrder();
        break;
      case 6:
        break;
    }

  }

  public void searchOrderByClient(){
    System.out.println(CLIENT_NAME_PROMPT);
    String clientName = ConsoleReader.readString();

    orderService.searchOrderByClient(clientName);
  }

  public void searchOrderByIssueDate(){
    System.out.println(ISSUEDATE_PROMPT);
    String issueDate = ConsoleReader.readString();
    System.out.println(ISSUEDATE_ON_BEFORE_AFTER_PROMPT);
    String onBeforeAfter = ConsoleReader.readString().toLowerCase();

    orderService.searchOrderByIssueDate(issueDate, onBeforeAfter);
  }

  public void createOrder(){
    System.out.println(BOOK_NAME_PROMPT);
    String bookName = ConsoleReader.readString();
    System.out.println(CLIENT_NAME_PROMPT);
    String clientName = ConsoleReader.readString();
    System.out.println(ISSUEDATE_PROMPT);
    String issueDate = ConsoleReader.readString();

    orderService.createOrder(bookName, clientName, issueDate);
  }

  public void deleteOrder(){
    System.out.println(ORDER_ID_PROMPT);
    String id = ConsoleReader.readString();

    orderService.deleteOrder(id);
  }

  public void showAllOrders(){
    String ordersString = orderService.showAllOrders();
    System.out.println(ordersString);
    showOrderMenu();
  }
}