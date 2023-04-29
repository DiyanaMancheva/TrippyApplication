package com.diyanamancheva.review;

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
  private ReviewService reviewService;

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
  public ResponseEntity<List<ReviewDto>> getReviewsByUser(@PathVariable int userId){
    List<Review> reviews = reviewService.getReviewsByUser(userId);
    List<ReviewDto> reviewDtos = new ArrayList<>();
    for(Review review : reviews){
      ReviewDto reviewDto = new ReviewDto(review.getId(), review.getUser(), review.getVenue(),
                                          review.getCreationDate(), review.getRating(), review.getText());
      reviewDtos.add(reviewDto);
    }

    return ResponseEntity.ok(reviewDtos);
  }

  @PostMapping("/reviews")
  public ResponseEntity<Void> createReview(@RequestBody @Valid ReviewRequest reviewRequest){
    Review review = reviewService.addReview(reviewRequest.getUserId(), reviewRequest.getVenueId(),
                            reviewRequest.getCreationDate(), reviewRequest.getRating(),
                            reviewRequest.getText());

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
