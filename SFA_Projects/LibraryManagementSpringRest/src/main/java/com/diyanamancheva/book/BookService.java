package com.diyanamancheva.book;

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

  public Book getBookById(int id) {
    return bookAccessor.readBookById(id);
  }

  public List<BookDto> getAllBooks() {
    List<Book> books = bookAccessor.readAllBooks();

    return bookMapper.mapBooksToDtos(books);
  }

  public void addBook(String title, int authorId, String publishingDate) {
    Book book = new Book(title, authorId, publishingDate);
    bookAccessor.addBook(book);
  }

  public BookDto editBook(int id, BookRequest bookRequest){

    Book book = getBookById(id);
    Book bookNew = new Book(id, bookRequest.getTitle(), bookRequest.getAuthorId(), bookRequest.getPublishingDate());
    bookAccessor.updateBook(bookNew);
    BookDto bookDto = new BookDto(book.getId(), book.getTitle(), book.getAuthorId(), book.getPublishingDate());

    return bookDto;
  }

  public BookDto removeBook(int id) {
    Book bookOld = getBookById(id);
    bookAccessor.deleteBook(id);

    return new BookDto(bookOld.getId(), bookOld.getTitle(), bookOld.getAuthorId(), bookOld.getPublishingDate());
  }
}
