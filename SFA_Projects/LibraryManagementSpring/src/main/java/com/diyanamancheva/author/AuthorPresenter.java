package com.diyanamancheva.author;

import com.diyanamancheva.util.ConsoleReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorPresenter {
  private static final String OPTIONS_MESSAGE =
    "------------------------------------------\n" +
    "Authors Menu: \n" +
    "------------------------------------------\n" +
    "1. Search for an author\n" +
    "2. Add a new author\n3. " +
    "Remove an author\n" +
    "4. Show all authors\n" +
    "5. Back\n" +
    "------------------------------------------\n" +
    "Your choice: \n" +
    "------------------------------------------";
  private static final String AUTHOR_NAME_PROMPT = "Enter author name: ";
  private static final String AUTHOR_ID_PROMPT = "Enter author id: ";
  private static final int MIN_MENU_OPTION = 1;
  private static final int MAX_MENU_OPTION = 5;

  private final AuthorService authorService;

  @Autowired
  public AuthorPresenter(AuthorService authorService){
    this.authorService = authorService;
  }
  public void showAuthorMenu(){
    System.out.println(OPTIONS_MESSAGE);

    int choice = ConsoleReader.readWithinRange(MIN_MENU_OPTION,MAX_MENU_OPTION);

    switch(choice){
      case 1:
        searchAuthor();
        break;
      case 2:
        addAuthor();
        break;
      case 3:
        deleteAuthor();
        break;
      case 4:
        showAllAuthors();
        break;
      case 5:
        //showBooksMenu();
        break;
    }
  }

  public void searchAuthor(){
    System.out.println(AUTHOR_NAME_PROMPT);
    String name = ConsoleReader.readString();

    List<Author> authors = authorService.getAllAuthors();

    for (Author author : authors) {
      if(author.getName().equals(name))  {
        System.out.println(author);
      }
    }
  }

  public void addAuthor(){
    System.out.println(AUTHOR_NAME_PROMPT);
    String name = ConsoleReader.readString();

    authorService.addAuthor(name);
  }

  public void deleteAuthor() {
    System.out.print(AUTHOR_ID_PROMPT);
    int id = ConsoleReader.readInt();
    int deletedAuthor = authorService.deleteAuthor(id);
    if (deletedAuthor == 1) {
      System.out.println("Author: " + id + " was deleted successfully");
    } else {
      System.out.println("Author: " + id + " was NOT deleted.");
    }
  }

  public void showAllAuthors(){
    List<Author> authors = authorService.getAllAuthors();
    for (Author author : authors) {
      System.out.println(author);
    }
  }
}
