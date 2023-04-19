package com.diyanamancheva.book;

import com.diyanamancheva.client.Client;
import com.diyanamancheva.client.ClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookService {
  private BookAccessor bookAccessor;
  private BookMapper bookMapper;

  @Autowired
  public BookService(BookAccessor bookAccessor, BookMapper bookMapper) {
    this.bookAccessor = bookAccessor;
    this.bookMapper = bookMapper;
  }

  public List<BookDto> getAllBooks() {
    List<Book> books = bookAccessor.readAllBooks();

    return bookMapper.mapBooksToDtos(books);
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

  public Book getBookById(int id, List<Book> books) {
    Book book = null;
    for (Book bookCurr : books) {
      if (bookCurr.getId() == id) {
        book = bookCurr;
        break;
      }
    }
    return book;
  }
}
