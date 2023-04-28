package com.diyanamancheva.user;

import com.diyanamancheva.exception.DatabaseConnectivityException;
import com.diyanamancheva.exception.EntityNotFoundException;
import com.diyanamancheva.exception.IdNotUniqueException;
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
public class UserAccessor {

  private static final Logger log = LoggerFactory.getLogger(UserAccessor.class);
  private UserMapper userMapper;
  private HikariDataSource dataSource;

  @Autowired
  public UserAccessor(UserMapper userMapper, HikariDataSource dataSource){
    this.userMapper = userMapper;
    this.dataSource = dataSource;
  }

  public List<User> readAllUsers(){
    ResultSet resultSet;
    List<User> users;

    String selectAllSQL = "SELECT * FROM users";

    try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {

      resultSet = statement.executeQuery(selectAllSQL);
      users = userMapper.mapResultSetToUsers(resultSet);
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return users;
  }

  public User readUserById(int id){
    ResultSet resultSet;
    List<User> users;

    String selectByIdSQL = "SELECT * FROM users WHERE user_id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(selectByIdSQL)) {

      preparedStatement.setInt(1, id);

      resultSet = preparedStatement.executeQuery();

      users = userMapper.mapResultSetToUsers(resultSet);

      if (users.size() > 1){
        log.error(String.format("More than one user with equal id = %d found.", id));
        throw new IdNotUniqueException(String.format("More than one user with equal id = %d found.", id));
      } else if (users.size() == 0) {
        log.error(String.format("No users with id %d found", id));
        throw new EntityNotFoundException(String.format("No user with id %d found", id));
      }
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return users.get(0);
  }
}
