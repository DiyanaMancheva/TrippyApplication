package com.diyanamancheva.book;

import com.diyanamancheva.exception.DatabaseConnectivityException;
import com.diyanamancheva.exception.IDNotUniqueException;
import com.diyanamancheva.exception.ItemNotFoundException;
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

    public Book readBookById(int id) {
      ResultSet resultSet;
      List<Book> books;

      final String SQL = "SELECT * FROM books WHERE book_id = ?";
      try (Connection connection = dataSource.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

        preparedStatement.setInt(1, id);

        resultSet = preparedStatement.executeQuery();

        books = bookMapper.mapResultSetToBooks(resultSet);
        if(books.size() > 1){
          throw new IDNotUniqueException(String.format("More than one books with equal id = %d found.", id));
        }else if (books.size() == 0){
          throw new ItemNotFoundException(String.format("No books with id %d found ", id));
        }
      } catch (SQLException e) {
          throw new DatabaseConnectivityException(e);
      }
      return books.get(0);
    }

    public List<Book> readAllBooks() {
      ResultSet resultSet;
      List<Book> books;
      try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
        resultSet = statement.executeQuery("SELECT * FROM books");
        books = bookMapper.mapResultSetToBooks(resultSet);
      } catch (SQLException e) {
          throw new DatabaseConnectivityException(e);
      }
      return books;
    }

    public void addBook(Book book) {
      final String insertSQL = "INSERT INTO Books(title, author_id, publishing_date) VALUES(?,?,?)";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setInt(2, book.getAuthorId());
        preparedStatement.setString(3, book.getPublishingDate());

        preparedStatement.executeUpdate();
      } catch (SQLException e) {
          throw new DatabaseConnectivityException(e);
      }
    }

    public int updateBook(Book book){
      final String updateSQL = "UPDATE books SET publishing_date = ? WHERE book_id = ?";

      try(Connection connection = dataSource.getConnection();
      PreparedStatement updateStatement = connection.prepareStatement(updateSQL)){

        updateStatement.setString(1, book.getPublishingDate());
        updateStatement.setInt(2, book.getId());

        return  updateStatement.executeUpdate();
      }catch(SQLException e){
        throw new DatabaseConnectivityException(e);
      }
    }

    public int deleteBook(int id) {
      final String deleteSQL = "DELETE FROM books WHERE book_id = ?";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

        deleteStatement.setInt(1, id);
        return deleteStatement.executeUpdate();
      } catch (SQLException e) {
          throw new DatabaseConnectivityException(e);
      }
    }
}
