package com.diyanamancheva.service;

import com.diyanamancheva.controller.request.review.ReviewRequest;
import com.diyanamancheva.dto.mapper.ReviewMapper;
import com.diyanamancheva.dto.review.ReviewDto;
import com.diyanamancheva.exception.EntityNotFoundException;
import com.diyanamancheva.model.City;
import com.diyanamancheva.model.Review;
import com.diyanamancheva.model.User;
import com.diyanamancheva.repository.ReviewRepository;
import com.diyanamancheva.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;
  private final UserService userService;
  private final VenueService venueService;


  @Autowired
  public ReviewService(
    ReviewRepository reviewRepository, ReviewMapper reviewMapper,
    UserService userService, VenueService venueService){
    this.reviewRepository = reviewRepository;
    this.reviewMapper = reviewMapper;
    this.userService = userService;
    this.venueService = venueService;
  }

  public List<ReviewDto> getAllReviews(){
    Iterable<Review> reviews = reviewRepository.findAll();
    List<ReviewDto> reviewDtos = reviewMapper.mapReviewsToDtos(reviews);

    return reviewDtos;
  }

  public Review getReviewById(int id){
    Review review = reviewRepository.findById(id)
                                    .orElseThrow(() -> new EntityNotFoundException(String.format("Review with id %d not found", id)));;

    return review;
  }

  public List<Review> getReviewsByUser(int userId){

    List<Review> reviews = new ArrayList<>();
    reviewRepository.findAll().forEach(review -> {
      if(review.getUser().getId() == userId){
        reviews.add(review);
      }
    });

    return reviews;
  }

  public List<Review> getReviewsByVenue(int venueId){

    List<Review> reviews = new ArrayList<>();
    reviewRepository.findAll().forEach(review -> {
      if(review.getVenue().getId() == venueId){
        reviews.add(review);
      }
    });

    return reviews;
  }

  public Review addReview(ReviewRequest reviewRequest) {
    Review reviewNew = reviewMapper.mapReviewRequestToReview(reviewRequest);
    reviewNew = reviewRepository.save(reviewNew);

    return reviewNew;
  }

  public ReviewDto updateReview(int id, ReviewRequest reviewRequest){
    Review review = getReviewById(id);
    User user = userService.getUserById(reviewRequest.getUserId());
    Venue venue = venueService.getVenueById(reviewRequest.getVenueId());

    Review reviewOld = new Review(review.getId(), user, venue, review.getCreationDate(),
                                  review.getRating(), review.getText());

    review.setUser(user);
    review.setVenue(venue);
    review.setRating(reviewRequest.getRating());
    review.setText(reviewRequest.getText());
    reviewRepository.save(review);

    ReviewDto reviewDto = reviewMapper.mapReviewToDto(reviewOld);
    return reviewDto;
  }

  public ReviewDto deleteReview(int id){
    Review review = getReviewById(id);
    reviewRepository.deleteById(id);
    ReviewDto reviewDto = new ReviewDto(review.getId(), review.getUser(), review.getVenue(),
                                        review.getCreationDate(), review.getRating(), review.getText());

    return reviewDto;
  }
}
