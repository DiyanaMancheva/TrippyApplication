package com.diyanamancheva.user;

import com.diyanamancheva.exception.DatabaseConnectivityException;
import com.diyanamancheva.exception.EntityNotFoundException;
import com.diyanamancheva.exception.IdNotUniqueException;
import com.diyanamancheva.review.Review;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.Date;
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

  public User addUser(User user){
    ResultSet resultSet;
    int userId;

    String insertSQL = "INSERT INTO user(username, city_id, email, joindate) VALUES (?,?,?,?)";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,
                                                                           Statement.RETURN_GENERATED_KEYS )) {

      preparedStatement.setString(1, user.getUsername());
      preparedStatement.setInt(2, user.getCity().getId());
      preparedStatement.setString(3, user.getEmail());
      preparedStatement.setDate(4, Date.valueOf(user.getJoinDate().toString()));

      log.debug("Trying to persist a new user");
      preparedStatement.executeUpdate();

      resultSet = preparedStatement.getGeneratedKeys();

      if (resultSet.next()){
        userId = resultSet.getInt(1);
      } else {
        log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
        throw new SQLException("Id was not retrieved from inserted user.");
      }
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    user.setId(userId);

    log.info(String.format("User with id %d successfully persisted", userId));
    return user;
  }
}
