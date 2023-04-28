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
import java.sql.Date;
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

  public Review addReview(Review review){
    ResultSet resultSet;
    int reviewId;

    String insertSQL = "INSERT INTO reviews(user_id, venue_id, creationdate, rating, text) VALUES (?,?,?,?,?)";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,
                                                                           Statement.RETURN_GENERATED_KEYS )) {

      preparedStatement.setInt(1, review.getUser().getId());
      preparedStatement.setInt(2, review.getVenue().getId());
      preparedStatement.setDate(3, Date.valueOf(review.getCreationDate().toString()));
      preparedStatement.setFloat(4, review.getRating());
      preparedStatement.setString(5, review.getText());

      log.debug("Trying to persist a new review");
      preparedStatement.executeUpdate();

      resultSet = preparedStatement.getGeneratedKeys();

      if (resultSet.next()){
        reviewId = resultSet.getInt(1);
      } else {
        log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
        throw new SQLException("Id was not retrieved from inserted review.");
      }
    }catch (SQLException e) {
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    review.setId(reviewId);

    log.info(String.format("Review with id %d successfully persisted", reviewId));
    return review;
  }

  public int updateReview(Review review){
    String updateSQL = "UPDATE reviews SET rating = ?, text = ? WHERE review_id = ?";

    try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)){

      preparedStatement.setFloat(1, review.getRating());
      preparedStatement.setString(2, review.getText());
      preparedStatement.setInt(3, review.getId());


      return  preparedStatement.executeUpdate();
    }catch(SQLException e){
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }
  }

  public int deleteReview(int id){
    String deleteSQL = "DELETE FROM reviews WHERE review_id = ?";

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
