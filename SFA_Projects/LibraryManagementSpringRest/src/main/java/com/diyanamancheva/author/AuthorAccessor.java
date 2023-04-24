package com.diyanamancheva.author;

import com.diyanamancheva.client.ClientAccessor;
import com.diyanamancheva.exception.DatabaseConnectivityException;
import com.diyanamancheva.exception.IDNotUniqueException;
import com.diyanamancheva.exception.ItemNotFoundException;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(AuthorAccessor.class);

    private AuthorMapper authorMapper;
    private HikariDataSource dataSource;

    @Autowired
    public AuthorAccessor(AuthorMapper authorMapper, HikariDataSource dataSource) {
      this.authorMapper = authorMapper;
      this.dataSource = dataSource;
    }

  public Author addAuthor(Author author) {
    final String insertSQL = "INSERT INTO Authors(Author_name) VALUES(?)";
    int authorId;

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,
                                                                           Statement.RETURN_GENERATED_KEYS)) {

      preparedStatement.setString(1, author.getName());

      log.debug("Trying to persist new item");
      preparedStatement.executeUpdate();

      ResultSet resultSet = preparedStatement.getGeneratedKeys();
      if(resultSet.next()){
        authorId = resultSet.getInt(1);
      }else{
        log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
        throw new SQLException("ID was NOT retrieved from inserted author.");
      }
    } catch (SQLException e) {
        log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
        throw new DatabaseConnectivityException(e);
    }

    author.setId(authorId);
    log.info(String.format("Author with id %d successfully persisted", authorId));
    return author;
  }

  public int updateAuthor(Author author) {
    final String UpdateSQL = "UPDATE authors SET author_name = ? WHERE author_id = ?";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement updateStatement = connection.prepareStatement(UpdateSQL)) {

      updateStatement.setString(1, author.getName());
      updateStatement.setInt(2, author.getId());

      return updateStatement.executeUpdate();
    } catch (SQLException e) {
        log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
        throw new DatabaseConnectivityException(e);
    }
  }

    public List<Author> readAllAuthors() {
      ResultSet resultSet;
      List<Author> authors;
      try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
        resultSet = statement.executeQuery("SELECT * FROM authors");
        authors = authorMapper.mapResultSetToAuthors(resultSet);
      } catch (SQLException e) {
          log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
          throw new DatabaseConnectivityException(e);
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
          log.error(String.format("More than one authors with equal id = %d found.", id));
          throw new IDNotUniqueException(String.format("More than one authors with equal id = %d found.", id));
        }else if (authors.size() == 0){
          log.error(String.format("No authors with id %d found", id));
          throw new ItemNotFoundException(String.format("No authors with id %d found ", id));
        }
      } catch (SQLException e) {
          log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
          throw new DatabaseConnectivityException(e);
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
          log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
          throw new DatabaseConnectivityException(e);
      }
    }
}
