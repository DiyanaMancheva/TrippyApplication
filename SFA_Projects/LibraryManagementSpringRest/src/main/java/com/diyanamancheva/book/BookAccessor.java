package com.diyanamancheva.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class BookAccessor {

    private BookMapper bookMapper;
    private HikariDataSource dataSource;


    @Autowired
    public BookAccessor(BookMapper bookMapper, HikariDataSource dataSource) {
      this.bookMapper = bookMapper;
      this.dataSource = dataSource;
    }

    public void addBook(Book book) {
      final String insertSQL = "INSERT INTO Books(title, author_id, publishing_date) VALUES(?,?,?)";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setInt(2, book.getAuthorId());
        preparedStatement.setString(3, book.getPublishingDate());

        preparedStatement.executeUpdate();

        System.out.println("Book: " + book.getTitle() + " was successfully added.");
      } catch (SQLException e) {
        System.out.println("Book: " + book.getTitle() + " was NOT added.");
        throw new RuntimeException(e);
      }
    }

    public List<Book> readAllBooks() {
      ResultSet resultSet;
      List<Book> books;
      try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
        resultSet = statement.executeQuery("SELECT * FROM books");
        books = bookMapper.mapResultSetToBooks(resultSet);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      return books;
    }

    public int deleteBook(int id) {
      final String deleteSQL = "DELETE FROM books WHERE book_id = ?";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

        deleteStatement.setInt(1, id);
        return deleteStatement.executeUpdate();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
}
