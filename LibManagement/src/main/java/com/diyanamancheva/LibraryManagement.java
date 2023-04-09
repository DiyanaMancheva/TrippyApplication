package com.diyanamancheva;

import com.diyanamancheva.book.BookPresenter;
import com.diyanamancheva.order.OrderPresenter;
import com.diyanamancheva.util.ConsoleReader;
import com.diyanamancheva.client.ClientPresenter;
import com.diyanamancheva.author.AuthorPresenter;

public class LibraryManagement {

  private static final ClientPresenter clientPresenter = new ClientPresenter();
  private static final AuthorPresenter authorPresenter = new AuthorPresenter();
  private static final BookPresenter bookPresenter = new BookPresenter();
  private static final OrderPresenter orderPresenter = new OrderPresenter();

  private static final int INPUT_MIN_VALUE = 1;

  private static final int INPUT_MAX_VALUE = 5;

  private static final String GREETING_MESSAGE= "Welcome to Library Management Application!\n" +
                                                "Please choose a menu, to proceed further:\n" +
                                                "1. Orders\n" +
                                                "2. Books\n" +
                                                "3. Clients\n" +
                                                "4. Authors\n" +
                                                "5. Exit\n" +
                                                "Your choice: ";

  public static void main(String[] args) {

    System.out.println(GREETING_MESSAGE);

    int choice = ConsoleReader.readWithinRange(INPUT_MIN_VALUE, INPUT_MAX_VALUE);

    switch (choice) {
      case 1:
        orderPresenter.showOrderMenu();
        break;
      case 2:
        bookPresenter.showBookMenu();
        break;
      case 3:
        clientPresenter.showClientMenu();
        break;
      case 4:
        authorPresenter.showAuthorMenu();
        break;
      case 5:
        break;
    }

  }
}