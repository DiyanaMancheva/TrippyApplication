package com.diyanamancheva.book;

import com.diyanamancheva.exception.DatabaseConnectivityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper {

  private static final Logger log = LoggerFactory.getLogger(BookAccessor.class);

  public List<Book> mapResultSetToBooks(ResultSet booksResultSet) {
    List<Book> booksList = new ArrayList<>();
    try (booksResultSet) {
      while (booksResultSet.next()) {
        int id = booksResultSet.getInt(1);
        String title = booksResultSet.getString(2);
        int authorId = booksResultSet.getInt(3);
        String publishingDate = booksResultSet.getString(4);


        Book book = new Book(title, authorId, publishingDate);

        book.setId(id);
        booksList.add(book);
      }
    } catch (SQLException e) {
      log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }
    return booksList;
  }

  public List<BookDto> mapBooksToDtos(List<Book> books){
    ArrayList<BookDto> bookDtos = new ArrayList<>();

    for(Book book : books){
      BookDto bookDto = new BookDto(book.getId(), book.getTitle(), book.getAuthorId(), book.getPublishingDate());
      bookDtos.add(bookDto);
    }
    return bookDtos;
  }
}
