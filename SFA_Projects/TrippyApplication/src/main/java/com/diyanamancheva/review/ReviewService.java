package com.diyanamancheva.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
  private ReviewAccessor reviewAccessor;
  private ReviewMapper reviewMapper;

  @Autowired
  public ReviewService(ReviewAccessor reviewAccessor, ReviewMapper reviewMapper){
    this.reviewAccessor = reviewAccessor;
    this.reviewMapper = reviewMapper;
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
}
