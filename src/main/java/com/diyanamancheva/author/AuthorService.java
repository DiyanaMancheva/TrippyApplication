package com.diyanamancheva.author;

import com.diyanamancheva.book.Book;
import com.diyanamancheva.author.Author;
import com.diyanamancheva.author.AuthorMapper;
import com.diyanamancheva.author.AuthorPresenter;
import com.diyanamancheva.client.Client;
import com.diyanamancheva.util.ItemAccessor;

import java.util.ArrayList;
import java.util.List;

public class AuthorService {
  private static final String ITEMS_FILE_PATH = "src/main/java/com/diyanamancheva/Authors.txt";
  private static final ItemAccessor authorAccessor = new ItemAccessor();
  private static final AuthorPresenter authorPresenter = new AuthorPresenter();
  private static final AuthorMapper authorMapper = new AuthorMapper();

  public String searchAuthor(String name){
    List<Author> authors = getAllAuthors();
    Author authorToBeSearched = getAuthorByName(name, authors);

    if(authorToBeSearched == null){
      System.out.println("Author: " + name + " not found.");
      authorPresenter.showAuthorMenu();
    }

    StringBuilder printAuthor = new StringBuilder();
    printAuthor.append("Name: " + authorToBeSearched.getName() + "\nBooks:\n");
    if(authorToBeSearched.getBooks().size() == 0){
      printAuthor.append("No books");
    }
    else{
      ArrayList<Book> books = authorToBeSearched.getBooks();
      int count = 0;
      for (Book book: books) {
        count++;
        printAuthor.append(count + ". " + book.getName()).append("\n");
      }
    }

    return printAuthor.toString();
  }

  public void addAuthor(String name) {
    List<Author>authors = getAllAuthors();
    Author author = getAuthorByName(name, authors);

    if(author != null){
      System.out.println("The author already exists!");
      authorPresenter.showAuthorMenu();
    }

    int id = authorAccessor.readAllItems(ITEMS_FILE_PATH).size() + 1;
    author = new Author(name);

    author.setId(id);

    String authorString = authorMapper.mapAuthorToString(author);
    authorAccessor.readItem(authorString, ITEMS_FILE_PATH);
    System.out.println("Author: " + name + " was successfully added.");
    authorPresenter.showAuthorMenu();
  }

  public void deleteAuthor(String name) {
    List<Author> authors = getAllAuthors();
    Author authorToBeDeleted = getAuthorByName(name, authors);

    if(authorToBeDeleted.getBooks().size() != 0){
      System.out.println("The Author has books to return.");
      authorPresenter.showAuthorMenu();
    }

    try{
      authors.remove(authorToBeDeleted);

      for (int i = authorToBeDeleted.getId()-1; i < authors.size(); i++) {
        authors.get(i).setId(i + 1);
      }

      String authorsString = authorMapper.mapAuthorListToString(authors);
      authorAccessor.overwriteFile(authorsString, ITEMS_FILE_PATH);
      System.out.println("Author: " + name + " was successfully deleted.");
      authorPresenter.showAuthorMenu();
    }
    catch (NullPointerException e){
      System.out.println("Author with such name was NOT found!");
      authorPresenter.showAuthorMenu();
    }
   }

  public String showAllAuthors(){
    List<Author> authors = getAllAuthors();
    return authorMapper.mapAuthorListToString(authors);
  }

  public Author getAuthorByName(String name, List<Author> authors) {
    Author author = null;
    for (Author authorFromList : authors) {
      if (authorFromList.getName().equals(name)) {
        author = authorFromList;
        break;
      }
    }
    return author;
  }

  public List<Author> getAllAuthors() {
    List<String> authorsString = authorAccessor.readAllItems(ITEMS_FILE_PATH);
    ArrayList<Author> authors = new ArrayList<Author>();
    for (String authorString : authorsString) {
      Author author = authorMapper.mapStringToAuthor(authorString);
      authors.add(author);
    }

    return authors;
  }
}
