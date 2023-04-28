package com.diyanamancheva.review;

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
public class ReviewAccessor {

  private static final Logger log = LoggerFactory.getLogger(ReviewAccessor.class);
  private ReviewMapper reviewMapper;
  private HikariDataSource dataSource;

  @Autowired
  public ReviewAccessor(ReviewMapper reviewMapper, HikariDataSource dataSource){
    this.reviewMapper = reviewMapper;
    this.dataSource = dataSource;
  }

  public List<Review> readAllReviews(){
    ResultSet resultSet;
    List<Review> reviews;

    String selectAllSQL = "SELECT * FROM reviews";

    try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {

      resultSet = statement.executeQuery(selectAllSQL);
      reviews = reviewMapper.mapResultSetToReviews(resultSet);
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return reviews;
  }

  public Review readReviewById(int id){
    ResultSet resultSet;
    List<Review> reviews;

    String selectByIdSQL = "SELECT * FROM reviews WHERE review_id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(selectByIdSQL)) {

      preparedStatement.setInt(1, id);

      resultSet = preparedStatement.executeQuery();

      reviews = reviewMapper.mapResultSetToReviews(resultSet);

      if (reviews.size() > 1){
        log.error(String.format("More than one review with equal id = %d found.", id));
        throw new IdNotUniqueException(String.format("More than one review with equal id = %d found.", id));
      } else if (reviews.size() == 0) {
        log.error(String.format("No review with id %d found", id));
        throw new EntityNotFoundException(String.format("No review with id %d found", id));
      }
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return reviews.get(0);
  }
}
