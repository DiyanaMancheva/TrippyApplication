package com.diyanamancheva.dto.mapper;

import com.diyanamancheva.dto.review.ReviewDto;
import com.diyanamancheva.exception.DatabaseConnectivityException;
import com.diyanamancheva.model.Review;
import com.diyanamancheva.model.User;
import com.diyanamancheva.repository.ReviewAccessor;
import com.diyanamancheva.service.UserService;
import com.diyanamancheva.model.Venue;
import com.diyanamancheva.service.VenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewMapper {

  private static final Logger log = LoggerFactory.getLogger(ReviewAccessor.class);
  private final UserService userService;
  private final VenueService venueService;

  @Autowired
  public ReviewMapper(UserService userService, VenueService venueService){
    this.userService = userService;
    this.venueService = venueService;
  }

  public List<Review> mapResultSetToReviews (ResultSet reviewsResultSet){
    List<Review> reviews = new ArrayList<>();
    try(reviewsResultSet){
      while (reviewsResultSet.next()){
        int id = reviewsResultSet.getInt(1);
        int userId = reviewsResultSet.getInt(2);
        int venueId = reviewsResultSet.getInt(3);
        String creationDateString = reviewsResultSet.getString(4);
        float rating = reviewsResultSet.getFloat(5);
        String text = reviewsResultSet.getString(6);

        User user = userService.getUserById(userId);
        Venue venue = venueService.getVenueById(venueId);
        LocalDate creationDate = LocalDate.parse(creationDateString);

        Review review = new Review(id, user, venue, creationDate, rating, text);

        reviews.add(review);
      }
    }catch (SQLException e){
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return reviews;
  }

  public List<ReviewDto> mapReviewsToDtos(List<Review> reviews){
    List<ReviewDto> reviewDtos = new ArrayList<>();

    for (Review review : reviews){
      ReviewDto reviewDto = new ReviewDto(review.getId(), review.getUser(),
                                    review.getVenue(), review.getCreationDate(),
                                    review.getRating(), review.getText());
      reviewDtos.add(reviewDto);
    }

    return reviewDtos;
  }
}
