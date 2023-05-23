package com.diyanamancheva.controller;

import com.diyanamancheva.service.ReviewService;
import com.diyanamancheva.model.Review;
import com.diyanamancheva.dto.review.ReviewByUserDto;
import com.diyanamancheva.dto.review.ReviewByVenueDto;
import com.diyanamancheva.dto.review.ReviewDto;
import com.diyanamancheva.controller.request.review.ReviewRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReviewController {
  private final ReviewService reviewService;

  @Autowired
  public ReviewController(ReviewService reviewService){
    this.reviewService = reviewService;
  }

  @GetMapping("/reviews")
  public ResponseEntity<List<ReviewDto>> getAllReviews(){
    List<ReviewDto> reviewDtos = reviewService.getAllReviews();

    return ResponseEntity.ok(reviewDtos);
  }

  @GetMapping("/reviews/{id}")
  public ResponseEntity<ReviewDto> getReviewById(@PathVariable int id){
    Review review = reviewService.getReviewById(id);
    ReviewDto reviewDto = new ReviewDto(review.getId(), review.getUser(), review.getVenue(),
                                        review.getCreationDate(), review.getRating(), review.getText());

    return ResponseEntity.ok(reviewDto);
  }

  @GetMapping("/users/{userId}/reviews")
  public ResponseEntity<List<ReviewByUserDto>> getReviewsByUser(@PathVariable int userId){
    List<Review> reviews = reviewService.getReviewsByUser(userId);
    List<ReviewByUserDto> reviewByUserDtos = new ArrayList<>();
    for(Review review : reviews){
      ReviewByUserDto reviewByUserDto = new ReviewByUserDto(review.getId(), review.getVenue().getName(),
                                          review.getCreationDate(), review.getRating(), review.getText());
      reviewByUserDtos.add(reviewByUserDto);
    }

    return ResponseEntity.ok(reviewByUserDtos);
  }

  @GetMapping("/venues/{venueId}/reviews")
  public ResponseEntity<List<ReviewByVenueDto>> getReviewsByVenue(@PathVariable int venueId){
    List<Review> reviews = reviewService.getReviewsByVenue(venueId);
    List<ReviewByVenueDto> reviewByVenueDtos = new ArrayList<>();
    for(Review review : reviews){
      ReviewByVenueDto reviewByVenueDto = new ReviewByVenueDto(review.getId(), review.getUser().getUsername(),
                                          review.getCreationDate(), review.getRating(), review.getText());
      reviewByVenueDtos.add(reviewByVenueDto);
    }

    return ResponseEntity.ok(reviewByVenueDtos);
  }

  @PostMapping("/reviews")
  public ResponseEntity<Void> createReview(@RequestBody @Valid ReviewRequest reviewRequest){
    Review review = reviewService.addReview(reviewRequest);

    URI location = UriComponentsBuilder.fromUriString("/reviews/{id}")
                                       .buildAndExpand(review.getId()).toUri();

    return ResponseEntity.created(location).build();
  }

  @PutMapping("/reviews/{id}")
  public ResponseEntity<ReviewDto> updateReview (@RequestBody @Valid ReviewRequest reviewRequest,
                                                 @PathVariable int id,
                                                 @RequestParam(required = false) boolean returnOld){

    ReviewDto reviewDto = reviewService.updateReview(id, reviewRequest);
    if (returnOld){
      return ResponseEntity.ok(reviewDto);
    }else{
      return ResponseEntity.noContent().build();
    }
  }

  @DeleteMapping("/reviews/{id}")
  public ResponseEntity<ReviewDto> deleteReview(@PathVariable int id,
                                            @RequestParam(required = false) boolean returnOld){
    ReviewDto reviewDto = reviewService.deleteReview(id);

    if(returnOld){
      return ResponseEntity.ok(reviewDto);
    } else {
      return ResponseEntity.noContent().build();
    }
  }
}
