package com.diyanamancheva.book;

import com.diyanamancheva.author.Author;
import com.diyanamancheva.author.AuthorMapper;
import com.diyanamancheva.author.AuthorService;
import com.diyanamancheva.book.Book;
import com.diyanamancheva.book.BookMapper;
import com.diyanamancheva.book.BookPresenter;
import com.diyanamancheva.util.ItemAccessor;

import java.util.ArrayList;
import java.util.List;

public class BookService {
  private static final String BOOKS_FILE_PATH = "src/main/java/com/diyanamancheva/Books.txt";
  private static final String AUTHORS_FILE_PATH = "src/main/java/com/diyanamancheva/Authors.txt";
  private static final ItemAccessor bookAccessor = new ItemAccessor();
  private static final BookPresenter bookPresenter = new BookPresenter();
  private static final BookMapper bookMapper = new BookMapper();
   private static final AuthorService authorService = new AuthorService();
  private static final AuthorMapper authorMapper = new AuthorMapper();

  public void searchBook(String name){
    List<Book> books = getAllBooks();
    Book bookToBeSearched = getBookByName(name, books);

    if(bookToBeSearched == null){
      System.out.println("Book: " + name + " not found.");
      bookPresenter.showBookMenu();
    }

    StringBuilder printBook = new StringBuilder();
    printBook.append("Name: " + bookToBeSearched.getName() +
                     "\nAuthor: " + bookToBeSearched.getAuthor().getName() +
                     "\nDate of Publishing: " + bookToBeSearched.getPublishingDate() +
                     "\nClient: ");
    if(bookToBeSearched.getClient() != null) printBook.append(bookToBeSearched.getClient().getName());

    System.out.println(printBook.toString());
    bookPresenter.showBookMenu();
  }

  public void addBook(String name, String authorName, String publishingDate) {
    List<Book> books = getAllBooks();
    Book book = getBookByName(name, books);

    if(book != null){
      System.out.println("The book already exists!");
      bookPresenter.showBookMenu();
    }

    int bookId = bookAccessor.readAllItems(BOOKS_FILE_PATH).size() + 1;

    List<Author> authors = authorService.getAllAuthors();
    Author author = authorService.getAuthorByName(authorName, authors);
    if (author == null){
      author = new Author(authorName);
      int authorId = bookAccessor.readAllItems(AUTHORS_FILE_PATH).size() + 1;
      author.setId(authorId);
      String authorString = authorMapper.mapAuthorToString(author);
      bookAccessor.readItem(authorString, AUTHORS_FILE_PATH);
      System.out.println("Author: " + name + " was successfully added.");
    }

    book = new Book(name, author, publishingDate);
    book.setId(bookId);

    String bookString = bookMapper.mapBookToString(book);
    bookAccessor.readItem(bookString, BOOKS_FILE_PATH);
    System.out.println("Book: " + name + " was successfully added.");
    bookPresenter.showBookMenu();
  }
  public void deleteBook(String name) {
    List<Book> books = getAllBooks();
    Book bookToBeDeleted = getBookByName(name, books);

    if(bookToBeDeleted == null){
      System.out.println("The book was not found.");
      bookPresenter.showBookMenu();
    }

    try{
      //List<Author> authors = authorService.getAllAuthors();
      //Author author = authorService.getAuthorByName(bookToBeDeleted.getAuthor().getName(), authors);
      //List<Book> booksByAuthor = author.getBooks();
      //for (Book book: booksByAuthor) {
      //  if(book.getName() == name) booksByAuthor.remove(book);
      //}
      //String booksByAuthorString = bookMapper.mapBookListToString(booksByAuthor);
      //author.getBooks() == booksByAuthorString

      books.remove(bookToBeDeleted);

      for (int i = bookToBeDeleted.getId()-1; i < books.size(); i++) {
        books.get(i).setId(i + 1);
      }

      String booksString = bookMapper.mapBookListToString(books);
      bookAccessor.overwriteFile(booksString, BOOKS_FILE_PATH);
      System.out.println("Book: " + name + " was successfully deleted.");
      bookPresenter.showBookMenu();
    }
    catch (NullPointerException e){
      System.out.println("Book with such name was NOT found!");
      bookPresenter.showBookMenu();
    }
   }

  public String showAllBooks(){
    List<Book> books = getAllBooks();
    return bookMapper.mapBookListToString(books);
  }

  public Book getBookByName(String name, List<Book> books) {
    Book book = null;
    for (Book bookFromList : books) {
      if (bookFromList.getName().equals(name)) {
        book = bookFromList;
        break;
      }
    }
    return book;
  }

  public List<Book> getAllBooks() {
    List<String> booksString = bookAccessor.readAllItems(BOOKS_FILE_PATH);
    ArrayList<Book> books = new ArrayList<Book>();
    for (String bookString : booksString) {
      Book book = bookMapper.mapStringToBook(bookString);
      books.add(book);
    }

    return books;
  }
}
