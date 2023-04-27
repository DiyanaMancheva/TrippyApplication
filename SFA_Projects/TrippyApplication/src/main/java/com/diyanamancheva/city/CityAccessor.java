package com.diyanamancheva.city;

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
public class CityAccessor {

  private static final Logger log = LoggerFactory.getLogger(CityAccessor.class);
  private CityMapper cityMapper;
  private HikariDataSource dataSource;

  @Autowired
  public CityAccessor(CityMapper cityMapper, HikariDataSource dataSource){
    this.cityMapper = cityMapper;
    this.dataSource = dataSource;
  }

  public List<City> readAllCities(){
    ResultSet resultSet;
    List<City> cities;

    String selectAllSQL = "SELECT * FROM cities";

    try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {

      resultSet = statement.executeQuery(selectAllSQL);
      cities = cityMapper.mapResultSetToCities(resultSet);
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return cities;
  }

  public City readCityById(int id){
    ResultSet resultSet;
    List<City> cities;

    String selectByIdSQL = "SELECT * FROM cities WHERE city_id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(selectByIdSQL)) {

      preparedStatement.setInt(1, id);

      resultSet = preparedStatement.executeQuery();

      cities = cityMapper.mapResultSetToCities(resultSet);

      if (cities.size() > 1){
        log.error(String.format("More than one city with equal id = %d found.", id));
        throw new IdNotUniqueException(String.format("More than one clients with equal id = %d found.", id));
      } else if (cities.size() == 0) {
        log.error(String.format("No city with id %d found", id));
        throw new EntityNotFoundException(String.format("No city with id %d found", id));
      }
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return cities.get(0);
  }

}
