package com.diyanamancheva.venue;

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
public class VenueAccessor {

  private static final Logger log = LoggerFactory.getLogger(VenueAccessor.class);
  private VenueMapper venueMapper;
  private HikariDataSource dataSource;

  @Autowired
  public VenueAccessor(VenueMapper venueMapper, HikariDataSource dataSource){
    this.venueMapper = venueMapper;
    this.dataSource = dataSource;
  }

  public List<Venue> readAllVenues(){
    ResultSet resultSet;
    List<Venue> venues;

    String selectAllSQL = "SELECT * FROM venues";

    try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {

      resultSet = statement.executeQuery(selectAllSQL);
      venues = venueMapper.mapResultSetToVenues(resultSet);
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return venues;
  }

  public Venue readVenueById(int id){
    ResultSet resultSet;
    List<Venue> venues;

    String selectByIdSQL = "SELECT * FROM venues WHERE venue_id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(selectByIdSQL)) {

      preparedStatement.setInt(1, id);

      resultSet = preparedStatement.executeQuery();

      venues = venueMapper.mapResultSetToVenues(resultSet);

      if (venues.size() > 1){
        log.error(String.format("More than one venue with equal id = %d found.", id));
        throw new IdNotUniqueException(String.format("More than one venue with equal id = %d found.", id));
      } else if (venues.size() == 0) {
        log.error(String.format("No venue with id %d found", id));
        throw new EntityNotFoundException(String.format("No venue with id %d found", id));
      }
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return venues.get(0);
  }
}
