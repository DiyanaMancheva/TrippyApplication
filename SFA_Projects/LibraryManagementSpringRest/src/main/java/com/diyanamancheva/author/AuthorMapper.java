package com.diyanamancheva.author;

import com.diyanamancheva.client.ClientAccessor;
import com.diyanamancheva.exception.DatabaseConnectivityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorMapper {
  private static final Logger log = LoggerFactory.getLogger(AuthorAccessor.class);

  public List<Author> mapResultSetToAuthors(ResultSet authorsResultSet) {
    List<Author> authorsList = new ArrayList<>();
    try (authorsResultSet) {
      while (authorsResultSet.next()) {
        int id = authorsResultSet.getInt(1);
        String name = authorsResultSet.getString(2);
        Author author = new Author(id, name);
        authorsList.add(author);
      }
    } catch (SQLException e) {
      log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
      throw new RuntimeException(e);
    }
    return authorsList;
  }

  public List<AuthorDto> mapAuthorsToDtos(List<Author> authors){
    ArrayList<AuthorDto> authorDtos = new ArrayList<>();
    for(Author author : authors){
      AuthorDto authorDto = new AuthorDto(author.getId(), author.getName());
      authorDtos.add(authorDto);
    }
    return authorDtos;
  }
}
