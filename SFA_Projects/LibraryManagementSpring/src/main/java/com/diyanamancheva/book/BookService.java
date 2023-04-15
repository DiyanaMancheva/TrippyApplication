package com.diyanamancheva.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
  private final BookAccessor bookAccessor;

  @Autowired
  public BookService(BookAccessor bookAccessor) {
    this.bookAccessor = bookAccessor;
  }

  public List<Book> getAllBooks() {
    return bookAccessor.readAllBooks();
  }
  public void addBook(String title, int authorId, String publishingDate) {
    Book book = new Book(title, authorId, publishingDate);
    bookAccessor.addBook(book);
  }

  public int deleteBook(int id) {
    return bookAccessor.deleteBook(id);
  }

  public Book getBookByTitle(String title, List<Book> books) {
    Book book = null;
    for (Book bookFromList : books) {
      if (bookFromList.getTitle().equals(title)) {
        book = bookFromList;
        break;
      }
    }
    return book;
  }
}
