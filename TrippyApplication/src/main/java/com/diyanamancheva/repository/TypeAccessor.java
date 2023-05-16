package com.diyanamancheva.repository;

import com.diyanamancheva.dto.mapper.TypeMapper;
import com.diyanamancheva.exception.DatabaseConnectivityException;
import com.diyanamancheva.exception.EntityNotFoundException;
import com.diyanamancheva.exception.IdNotUniqueException;
import com.diyanamancheva.model.Type;
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
public class TypeAccessor {

  private static final Logger log = LoggerFactory.getLogger(TypeAccessor.class);
  private final TypeMapper typeMapper;
  private final HikariDataSource dataSource;

  @Autowired
  public TypeAccessor(TypeMapper typeMapper, HikariDataSource dataSource){
    this.typeMapper = typeMapper;
    this.dataSource = dataSource;
  }

  public List<Type> readAllTypes(){
    ResultSet resultSet;
    List<Type> types;

    String selectAllSQL = "SELECT * FROM types";

    try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {

      resultSet = statement.executeQuery(selectAllSQL);
      types = typeMapper.mapResultSetToTypes(resultSet);
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return types;
  }

  public Type readTypeById(int id){
    ResultSet resultSet;
    List<Type> types;

    String selectByIdSQL = "SELECT * FROM types WHERE type_id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(selectByIdSQL)) {

      preparedStatement.setInt(1, id);

      resultSet = preparedStatement.executeQuery();

      types = typeMapper.mapResultSetToTypes(resultSet);

      if (types.size() > 1){
        log.error(String.format("More than one type with equal id = %d found.", id));
        throw new IdNotUniqueException(String.format("More than one type with equal id = %d found.", id));
      } else if (types.size() == 0) {
        log.error(String.format("No type with id %d found", id));
        throw new EntityNotFoundException(String.format("No type with id %d found", id));
      }
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return types.get(0);
  }
}
