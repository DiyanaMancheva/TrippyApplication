package com.diyanamancheva.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

  @PostMapping("/reviews")
  public ResponseEntity<Void> createReview(@RequestBody @Valid ReviewRequest reviewRequest){
    Review review = reviewService.addReview(reviewRequest.getUserId(), reviewRequest.getVenueId(),
                            reviewRequest.getCreationDate(), reviewRequest.getRating(),
                            reviewRequest.getText());

    URI location = UriComponentsBuilder.fromUriString("/reviews/{id}")
                                       .buildAndExpand(review.getId()).toUri();

    return ResponseEntity.created(location).build();
  }
}
