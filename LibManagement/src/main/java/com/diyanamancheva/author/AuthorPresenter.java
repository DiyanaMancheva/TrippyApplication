package com.diyanamancheva.author;

import com.diyanamancheva.LibraryManagement;
import com.diyanamancheva.author.AuthorService;
import com.diyanamancheva.util.ConsoleReader;

public class AuthorPresenter {
  private static final String OPTIONS_MESSAGE =
    "Choose what to do: \n1. Search for an author\n2. Add a new author\n3. " +
    "Remove an author\n4. Show all authors\n5. Exit";
  private static final String AUTHOR_NAME_PROMPT = "Enter author name: ";
  private static final String AUTHOR_EDIT_NAME_PROMPT = "Enter new name: ";
  private static final int MIN_MENU_OPTION = 1;
  private static final int MAX_MENU_OPTION = 5;

  private static final AuthorService authorService = new AuthorService();

  private static final LibraryManagement libraryManagement = new LibraryManagement();

  public AuthorPresenter(){

  }
  public void showAuthorMenu(){
    System.out.println(OPTIONS_MESSAGE);

    int choise = ConsoleReader.readWithinRange(MIN_MENU_OPTION,MAX_MENU_OPTION);

    switch(choise){
      case 1:
        searchAuthor();
        break;
      case 2:
        addAuthor();
        break;
      case 3:
        deleteAuthor();
      case 4:
        showAllAuthors();
        break;
      case 5:
        break;
    }

  }

  public void searchAuthor(){
    System.out.println(AUTHOR_NAME_PROMPT);
    String name = ConsoleReader.readString();

    String result = authorService.searchAuthor(name);
    System.out.println(result);
  }

  public void addAuthor(){
    System.out.println(AUTHOR_NAME_PROMPT);
    String name = ConsoleReader.readString();

    authorService.addAuthor(name);
  }

  public void deleteAuthor(){
    System.out.println(AUTHOR_NAME_PROMPT);
    String name = ConsoleReader.readString();

    authorService.deleteAuthor(name);
  }

  public void showAllAuthors(){
    String authorsString = authorService.showAllAuthors();
    System.out.println(authorsString);
  }

}
