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

  public List<Review> getReviewsByUser(int userId){
    User user = userService.getUserById(userId);
    List<Review> reviews = reviewAccessor.readReviewsByUser(userId);

    return reviews;
  }

  public List<Review> getReviewsByVenue(int venueId){
    Venue venue = venueService.getVenueById(venueId);
    List<Review> reviews = reviewAccessor.readReviewsByVenue(venueId);

    return reviews;
  }

  public Review addReview(int userId, int venueId, LocalDate creationDate,
                        float rating, String text) {
    User user = userService.getUserById(userId);
    Venue venue = venueService.getVenueById(venueId);

    Review review = new Review(user, venue, creationDate, rating, text);
    reviewAccessor.addReview(review);

    return review;
  }

  public ReviewDto updateReview(int id, ReviewRequest reviewRequest){
    Review review = getReviewById(id);
    User user = userService.getUserById(reviewRequest.getUserId());
    Venue venue = venueService.getVenueById(reviewRequest.getVenueId());

    Review reviewNew = new Review(id, user, venue, reviewRequest.getCreationDate(),
                                  reviewRequest.getRating(), reviewRequest.getText());

    reviewAccessor.updateReview(reviewNew);

    ReviewDto reviewDto = new ReviewDto(review.getId(), review.getUser(), review.getVenue(),
                                        review.getCreationDate(), review.getRating(), review.getText());

    return reviewDto;
  }

  public ReviewDto deleteReview(int id){
    Review review = getReviewById(id);
    reviewAccessor.deleteReview(id);
    ReviewDto reviewDto = new ReviewDto(review.getId(), review.getUser(), review.getVenue(),
                                        review.getCreationDate(), review.getRating(), review.getText());

    return reviewDto;
  }
}
