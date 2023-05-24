package com.diyanamancheva.dto.mapper;

import com.diyanamancheva.controller.request.review.ReviewRequest;
import com.diyanamancheva.dto.review.ReviewDto;
import com.diyanamancheva.model.Review;
import com.diyanamancheva.model.User;
import com.diyanamancheva.repository.ReviewRepository;
import com.diyanamancheva.service.UserService;
import com.diyanamancheva.model.Venue;
import com.diyanamancheva.service.VenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewMapper {

  private static final Logger log = LoggerFactory.getLogger(ReviewRepository.class);
  private final UserService userService;
  private final VenueService venueService;

  @Autowired
  public ReviewMapper(UserService userService, VenueService venueService){
    this.userService = userService;
    this.venueService = venueService;
  }

  public Review mapReviewRequestToReview (ReviewRequest reviewRequest){
    User user = userService.getUserById(reviewRequest.getUserId());
    Venue venue = venueService.getVenueById(reviewRequest.getVenueId());

    return new Review(user, venue, reviewRequest.getCreationDate(),
                      reviewRequest.getRating(), reviewRequest.getText());
  }

  public List<ReviewDto> mapReviewsToDtos(Iterable<Review> reviews){
    List<ReviewDto> reviewDtos = new ArrayList<>();

    reviews.forEach(review -> reviewDtos.add(mapReviewToDto(review)));

    return reviewDtos;
  }

  public ReviewDto mapReviewToDto(Review review) {

    return new ReviewDto(review.getId(), review.getUser(), review.getVenue(),
                         review.getCreationDate(), review.getRating(), review.getText());
  }
}
