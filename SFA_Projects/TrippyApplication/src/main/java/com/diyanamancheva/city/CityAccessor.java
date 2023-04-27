package com.diyanamancheva.city;

import com.diyanamancheva.exception.DatabaseConnectivityException;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
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
      log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return cities;
  }

}
