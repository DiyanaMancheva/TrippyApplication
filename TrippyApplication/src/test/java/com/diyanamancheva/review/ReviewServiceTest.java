package com.diyanamancheva.review;

import com.diyanamancheva.model.City;
import com.diyanamancheva.model.Review;
import com.diyanamancheva.model.Type;
import com.diyanamancheva.model.User;
import com.diyanamancheva.model.Venue;
import com.diyanamancheva.repository.ReviewAccessor;
import com.diyanamancheva.repository.VenueAccessor;
import com.diyanamancheva.service.ReviewService;
import com.diyanamancheva.service.VenueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTest {

  @Mock
  private ReviewAccessor reviewAccessor;

  @InjectMocks
  private ReviewService reviewService;

  private static final int REVIEW_ID = 1;
  private static final int USER_ID = 1;
  private static final String USERNAME = "TestUsername";
  private static final int CITY_ID = 1;
  private static final String CITY_NAME = "TestCity";
  private static final String EMAIL = "test.test@gmail.com";
  private static final LocalDate JOINDATE = LocalDate.parse("2023-05-01");
  private static final int TYPE_ID = 1;
  private static final String TYPE_NAME = "TestType";
  private static final int VENUE_ID = 1;
  private static final String VENUE_NAME = "TestVenue";
  private static final String ADDRESS = "1 Test Str.";
  private static final int RATING = 1;
  private static final int REVIEWS = 0;
  private static final LocalDate CREATIONDATE = LocalDate.parse("2023-05-01");
  private static final String TEXT = "TestText";

  @Test
  public void deleteReview_success() {
    City cityTest = new City(CITY_ID, CITY_NAME);
    User userTest = new User(USER_ID, USERNAME, cityTest, EMAIL, JOINDATE);
    Type typeTest = new Type(TYPE_ID, TYPE_NAME);
    Venue venueTest = new Venue(VENUE_ID, VENUE_NAME, typeTest, cityTest, ADDRESS, RATING, REVIEWS);

    when(reviewService.getReviewById(REVIEW_ID)).thenReturn(new Review(REVIEW_ID, userTest, venueTest,
                                                                       CREATIONDATE, RATING, TEXT));
    reviewService.deleteReview(REVIEW_ID);

    verify(reviewAccessor, times(1)).readReviewById(REVIEW_ID);
    verify(reviewAccessor, times(1)).deleteReview(REVIEW_ID);
  }

}
