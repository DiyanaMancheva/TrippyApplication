package com.diyanamancheva.repository;

import com.diyanamancheva.dto.mapper.VenueMapper;
import com.diyanamancheva.exception.DatabaseConnectivityException;
import com.diyanamancheva.exception.EntityNotFoundException;
import com.diyanamancheva.exception.IdNotUniqueException;
import com.diyanamancheva.exception.UserExistsException;
import com.diyanamancheva.model.Venue;
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
  private final VenueMapper venueMapper;
  private final HikariDataSource dataSource;

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

  public List<Venue> readVenuesByType(int typeId){
    ResultSet resultSet;
    List<Venue> venues;

    String selectByTypeIdSQL = "SELECT * FROM venues WHERE type_id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(selectByTypeIdSQL)) {

      preparedStatement.setInt(1, typeId);

      resultSet = preparedStatement.executeQuery();

      venues = venueMapper.mapResultSetToVenues(resultSet);

      if (venues.size() == 0) {
        log.info(String.format("No venues with type id %d found", typeId));
        throw new EntityNotFoundException(String.format("No venues with type id %d found", typeId));
      }
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return venues;
  }

  public List<Venue> readVenuesByCity(int cityId){
    ResultSet resultSet;
    List<Venue> venues;

    String selectByCityIdSQL = "SELECT * FROM venues WHERE city_id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(selectByCityIdSQL)) {

      preparedStatement.setInt(1, cityId);

      resultSet = preparedStatement.executeQuery();

      venues = venueMapper.mapResultSetToVenues(resultSet);

      if (venues.size() == 0) {
        log.info(String.format("No venues with city id %d found", cityId));
        throw new EntityNotFoundException(String.format("No venues with city id %d found", cityId));
      }
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return venues;
  }

  public void readVenuesByNameAndCity(String name, int city){
    ResultSet resultSet;
    List<Venue> venues;

    String selectByIdSQL = "SELECT * FROM venues WHERE venue_name = ? AND city_id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(selectByIdSQL)) {

      preparedStatement.setString(1, name);
      preparedStatement.setInt(2, city);

      resultSet = preparedStatement.executeQuery();

      venues = venueMapper.mapResultSetToVenues(resultSet);

      if (venues.size() > 0){
        log.error("Venue with name = " + name + " and city = " + city + " already exists.");
        throw new UserExistsException("Venue with name = " + name + " and city = " + city + " already exists.");
      }
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }
  }

  public Venue addVenue(Venue venue){
    ResultSet resultSet;
    int venueId;

    String insertSQL = "INSERT INTO venues(type_id, city_id, venue_name, address, rating, reviewscount) VALUES (?,?,?,?,?,?)";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,
                                                                           Statement.RETURN_GENERATED_KEYS )) {

      preparedStatement.setInt(1, venue.getType().getId());
      preparedStatement.setInt(2, venue.getCity().getId());
      preparedStatement.setString(3, venue.getName());
      preparedStatement.setString(4, venue.getAddress());
      preparedStatement.setFloat(5, venue.getRating());
      preparedStatement.setInt(6, venue.getReviews());

      log.debug("Trying to persist a new venue");
      preparedStatement.executeUpdate();

      resultSet = preparedStatement.getGeneratedKeys();

      if (resultSet.next()){
        venueId = resultSet.getInt(1);
      } else {
        log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
        throw new SQLException("Id was not retrieved from inserted venue.");
      }
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    venue.setId(venueId);

    log.info(String.format("Venue with id %d successfully persisted", venueId));
    return venue;
  }

  public int updateVenue(Venue venue){
    String updateSQL = "UPDATE venues SET address = ? WHERE venue_id = ?";

    try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)){

      preparedStatement.setString(1, venue.getAddress());
      preparedStatement.setInt(2, venue.getId());


      return  preparedStatement.executeUpdate();
    }catch(SQLException e){
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }
  }

  public int updateVenueRatingAndReviews(Venue venue){
    String updateRatingReviewsSQL = "UPDATE venues SET rating = ?, reviewscount = ? WHERE venue_id = ?";

    try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(updateRatingReviewsSQL)){

      preparedStatement.setFloat(1, venue.getRating());
      preparedStatement.setInt(2, venue.getReviews());
      preparedStatement.setInt(3, venue.getId());


      return  preparedStatement.executeUpdate();
    }catch(SQLException e){
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }
  }

  public int deleteVenue(int id){
    String deleteSQL = "DELETE FROM venues WHERE venue_id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

      preparedStatement.setInt(1, id);

      return preparedStatement.executeUpdate();

    } catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }
  }
}