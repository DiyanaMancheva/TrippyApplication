package com.diyanamancheva.review;

import com.diyanamancheva.user.User;
import com.diyanamancheva.user.UserService;
import com.diyanamancheva.venue.Venue;
import com.diyanamancheva.venue.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {
  private ReviewAccessor reviewAccessor;
  private ReviewMapper reviewMapper;
  private UserService userService;
  private VenueService venueService;


  @Autowired
  public ReviewService(ReviewAccessor reviewAccessor, ReviewMapper reviewMapper,
                       UserService userService, VenueService venueService){
    this.reviewAccessor = reviewAccessor;
    this.reviewMapper = reviewMapper;
    this.userService = userService;
    this.venueService = venueService;
  }

  public List<ReviewDto> getAllReviews(){
    List<Review> reviews = reviewAccessor.readAllReviews();
    List<ReviewDto> reviewDtos = reviewMapper.mapReviewsToDtos(reviews);

    return reviewDtos;
  }

  public Review getReviewById(int id){
    Review review = reviewAccessor.readReviewById(id);

    return review;
  }

  public Review addReview(int userId, int venueId, LocalDate creationDate,
                        float rating, String text) {
    User user = userService.getUserById(userId);
    Venue venue = venueService.getVenueById(venueId);

    Review review = new Review(user, venue, creationDate, rating, text);
    reviewAccessor.addReview(review);

    return review;
  }
}
