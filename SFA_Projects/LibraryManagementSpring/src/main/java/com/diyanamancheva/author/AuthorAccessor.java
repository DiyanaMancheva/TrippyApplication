package com.diyanamancheva.author;

import com.diyanamancheva.configuration.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class AuthorAccessor {

    private static final String FILE_NOT_FOUND_MESSAGE = "File not found with path ";

    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorAccessor(AuthorMapper authorMapper) {
      this.authorMapper = authorMapper;
    }

    public void addAuthor(Author author) {
      final String insertSQL = "INSERT INTO Authors(author_name) VALUES(?)";

      try (Connection connection = DataSource.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

        preparedStatement.setString(1, author.getName());

        preparedStatement.executeUpdate();

        System.out.println("Author: " + author.getName() + " was successfully added.");
      } catch (SQLException e) {
        System.out.println("Author: " + author.getName() + " was NOT added.");
        throw new RuntimeException(e);
      }
    }

    public List<Author> readAllAuthors() {
      ResultSet resultSet;
      List<Author> authors;
      try (Connection connection = DataSource.getConnection(); Statement statement = connection.createStatement()) {
        resultSet = statement.executeQuery("SELECT * FROM authors");
        authors = authorMapper.mapResultSetToAuthors(resultSet);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      return authors;
    }

    public int deleteAuthor(int id) {
      final String deleteSQL = "DELETE FROM authors WHERE author_id = ?";

      try (Connection connection = DataSource.getConnection();
           PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

        deleteStatement.setInt(1, id);
        return deleteStatement.executeUpdate();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
}
