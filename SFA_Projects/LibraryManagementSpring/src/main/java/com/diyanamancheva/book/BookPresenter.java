package com.diyanamancheva.book;

import com.diyanamancheva.util.ConsoleReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookPresenter {
  private static final String OPTIONS_MESSAGE =
    "------------------------------------------\n" +
    "Books Menu: \n" +
    "------------------------------------------\n" +
    "1. Search for a book\n" +
    "2. Add a new book\n" +
    "3. Delete a book\n" +
    "4. Show all books\n" +
    "5. Back\n" +
    "------------------------------------------\n" +
    "Your choice: \n" +
    "------------------------------------------";

  private static final String BOOK_TITLE_PROMPT = "Enter book title: ";
  private static final String BOOK_ID_PROMPT = "Enter book id: ";
  private static final String AUTHOR_ID_PROMPT = "Enter author id: ";
  private static final String BOOK_PUBLISHINGDATE_PROMPT = "Enter publishing date: ";

  private static final int MIN_MENU_OPTION = 1;
  private static final int MAX_MENU_OPTION = 5;

  private final BookService bookService;

  @Autowired
  public BookPresenter(BookService bookService){
    this.bookService = bookService;
  }
  public void showBookMenu(){
    System.out.println(OPTIONS_MESSAGE);

    int choice = ConsoleReader.readWithinRange(MIN_MENU_OPTION,MAX_MENU_OPTION);

    switch(choice){
      case 1:
        searchBook();
        break;
      case 2:
        addBook();
        break;
      case 3:
        deleteBook();
        break;
      case 4:
        showAllBooks();
        break;
      case 5:
        //showBooksMenu();
        break;
    }
  }

  public void searchBook(){
    System.out.println(BOOK_TITLE_PROMPT);
    String title = ConsoleReader.readString();

    List<Book> books = bookService.getAllBooks();

    Book book = bookService.getBookByTitle(title, books);

    System.out.println(book != null ? book : "Book: " + title + " was NOT found.");
  }

  public void addBook(){
    System.out.println(BOOK_TITLE_PROMPT);
    String title = ConsoleReader.readString();
    System.out.println(AUTHOR_ID_PROMPT);
    int authorId = ConsoleReader.readInt();
    System.out.println(BOOK_PUBLISHINGDATE_PROMPT);
    String publishingDate = ConsoleReader.readString();

    bookService.addBook(title, authorId, publishingDate);
  }

  public void deleteBook() {
    System.out.print(BOOK_ID_PROMPT);
    int id = ConsoleReader.readInt();
    int deletedBook = bookService.deleteBook(id);
    if (deletedBook == 1) {
      System.out.println("Book: " + id + " was deleted successfully");
    } else {
      System.out.println("Book: " + id + " was NOT deleted.");
    }
  }

  public void showAllBooks(){
    List<Book> books = bookService.getAllBooks();
    for (Book book : books) {
      System.out.println(book);
    }
  }
}
