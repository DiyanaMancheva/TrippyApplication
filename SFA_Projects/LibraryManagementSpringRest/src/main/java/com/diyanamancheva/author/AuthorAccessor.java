package com.diyanamancheva.author;

import com.diyanamancheva.client.Client;
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
public class AuthorAccessor {
    private AuthorMapper authorMapper;
    private HikariDataSource dataSource;

    @Autowired
    public AuthorAccessor(AuthorMapper authorMapper, HikariDataSource dataSource) {
      this.authorMapper = authorMapper;
      this.dataSource = dataSource;
    }

    public void addAuthor(Author author) {
      final String insertSQL = "INSERT INTO Authors(author_name) VALUES(?)";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

        preparedStatement.setString(1, author.getName());

        preparedStatement.executeUpdate();

        System.out.println("Author: " + author.getName() + " was successfully added.");
      } catch (SQLException e) {
        System.out.println("Author: " + author.getName() + " was NOT added.");
        throw new RuntimeException(e);
      }
    }

    public int updateAuthor(Author author) {
      final String UpdateSQL = "UPDATE authors SET author_name = ? WHERE author_id = ?";
      try (Connection connection = dataSource.getConnection();
           PreparedStatement updateStatement = connection.prepareStatement(UpdateSQL)) {

        updateStatement.setString(1, author.getName());
        updateStatement.setInt(2, author.getId());

        return updateStatement.executeUpdate();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }

    public List<Author> readAllAuthors() {
      ResultSet resultSet;
      List<Author> authors;
      try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
        resultSet = statement.executeQuery("SELECT * FROM authors");
        authors = authorMapper.mapResultSetToAuthors(resultSet);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      return authors;
    }

    public Author readAuthorById(int id) {
      ResultSet resultSet;
      List<Author> authors;

      final String SQL = "SELECT * FROM authors WHERE author_id = ?";
      try (Connection connection = dataSource.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

        preparedStatement.setInt(1, id);

        resultSet = preparedStatement.executeQuery();

        authors = authorMapper.mapResultSetToAuthors(resultSet);
        if(authors.size() > 1){
          throw new SQLException("More than one authors with equal id");
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      return authors.get(0);
    }

    public int deleteAuthor(int id) {
      final String deleteSQL = "DELETE FROM authors WHERE author_id = ?";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

        deleteStatement.setInt(1, id);
        return deleteStatement.executeUpdate();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
}
