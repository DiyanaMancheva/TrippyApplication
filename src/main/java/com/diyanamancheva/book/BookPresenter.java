package com.diyanamancheva.book;

import com.diyanamancheva.LibraryManagement;
import com.diyanamancheva.util.ConsoleReader;

public class BookPresenter {
  private static final String OPTIONS_MESSAGE =
    "Choose what to do: \n1. Search for a book\n2. Add a new book\n3. " +
    "Edit a book\n4. Remove a book\n5. Show all books\n6. Back";
  private static final String BOOK_NAME_PROMPT = "Enter book name: ";
  private static final String BOOK_AUTHOR_PROMPT = "Enter author name: ";
  private static final String BOOK_PUBLISHING_DATE_PROMPT = "Enter date of publishing: ";
  private static final String BOOK_EDIT_NAME_PROMPT = "Enter new name: ";
  private static final String BOOK_EDIT_AUTHOR_PROMPT = "Enter author new name: ";
  private static final String BOOK_EDIT_PUBLISHING_DATE_PROMPT = "Enter new date of publishing: ";
  private static final int MIN_MENU_OPTION = 1;
  private static final int MAX_MENU_OPTION = 6;

  private static final BookService bookService = new BookService();

  private static final LibraryManagement libraryManagement = new LibraryManagement();

  public BookPresenter(){

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
      case 3:
        //editBook();
        break;
      case 4:
        deleteBook();
        break;
      case 5:
        showAllBooks();
        break;
      case 6:
        //back
        break;
    }

  }

  public void searchBook(){
    System.out.println(BOOK_NAME_PROMPT);
    String name = ConsoleReader.readString();

    bookService.searchBook(name);
  }

  public void addBook(){
    System.out.println(BOOK_NAME_PROMPT);
    String name = ConsoleReader.readString();
    System.out.println(BOOK_AUTHOR_PROMPT);
    String author = ConsoleReader.readString();
    System.out.println(BOOK_PUBLISHING_DATE_PROMPT);
    String publishingDate = ConsoleReader.readString();

    bookService.addBook(name, author, publishingDate);
  }

  public void deleteBook(){
    System.out.println(BOOK_NAME_PROMPT);
    String name = ConsoleReader.readString();

    bookService.deleteBook(name);
  }

  public void showAllBooks(){
    String booksString = bookService.showAllBooks();
    System.out.println(booksString);
  }
}
