package com.diyanamancheva.book;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper {

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
      throw new RuntimeException(e);
    }
    return booksList;
  }
}
