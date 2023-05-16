package com.diyanamancheva.review;

import com.diyanamancheva.city.City;
import com.diyanamancheva.type.Type;
import com.diyanamancheva.user.User;
import com.diyanamancheva.venue.Venue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class ReviewControllerTest {
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
  private static final float RATING = 1;
  private static final int REVIEWS = 0;
  private static final LocalDate CREATIONDATE = LocalDate.parse("2023-05-01");
  private static final String TEXT = "TestText";

  private MockMvc mockMvc;

  @Mock
  private ReviewService reviewService;

  @InjectMocks
  private ReviewController reviewController;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
  }

  @Test
  public void getAllReviews_singleReview_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    User userTest = new User(USER_ID, USERNAME, cityTest, EMAIL, JOINDATE);
    Type typeTest = new Type(TYPE_ID, TYPE_NAME);
    Venue venueTest = new Venue(VENUE_ID, VENUE_NAME, typeTest, cityTest, ADDRESS, RATING, REVIEWS);
    when(reviewService.getAllReviews()).thenReturn(Collections.singletonList(new ReviewDto(REVIEW_ID, userTest, venueTest,
                                                                                        CREATIONDATE, RATING, TEXT)));
    mockMvc.perform(get("/reviews"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(REVIEW_ID))
           .andExpect(jsonPath("$[0].user.id").value(USER_ID))
           .andExpect(jsonPath("$[0].user.username").value(USERNAME))
           .andExpect(jsonPath("$[0].venue.id").value(VENUE_ID))
           .andExpect(jsonPath("$[0].venue.name").value(VENUE_NAME))
           .andExpect(jsonPath("$[0].creationDate[0]").value("2023"))
           .andExpect(jsonPath("$[0].creationDate[1]").value("5"))
           .andExpect(jsonPath("$[0].creationDate[2]").value("1"))
           .andExpect(jsonPath("$[0].rating").value(RATING))
           .andExpect(jsonPath("$[0].text").value(TEXT));
  }

  @Test
  public void getAllReviews_emptyList_success() throws Exception {
    when(reviewService.getAllReviews()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/reviews"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").exists())
           .andExpect(jsonPath("$[0]").doesNotExist());
  }

  @Test
  public void getReviewById_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    User userTest = new User(USER_ID, USERNAME, cityTest, EMAIL, JOINDATE);
    Type typeTest = new Type(TYPE_ID, TYPE_NAME);
    Venue venueTest = new Venue(VENUE_ID, VENUE_NAME, typeTest, cityTest, ADDRESS, RATING, REVIEWS);
    when(reviewService.getReviewById(REVIEW_ID)).thenReturn(new Review(REVIEW_ID, userTest, venueTest,
                                                                          CREATIONDATE, RATING, TEXT));

    mockMvc.perform(get("/reviews/" + REVIEW_ID))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(REVIEW_ID))
           .andExpect(jsonPath("$.user.id").value(USER_ID))
           .andExpect(jsonPath("$.user.username").value(USERNAME))
           .andExpect(jsonPath("$.venue.id").value(VENUE_ID))
           .andExpect(jsonPath("$.venue.name").value(VENUE_NAME))
           .andExpect(jsonPath("$.creationDate[0]").value("2023"))
           .andExpect(jsonPath("$.creationDate[1]").value("5"))
           .andExpect(jsonPath("$.creationDate[2]").value("1"))
           .andExpect(jsonPath("$.rating").value(RATING))
           .andExpect(jsonPath("$.text").value(TEXT));
  }


  @Test
  public void deleteReview_noResponse_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    User userTest = new User(USER_ID, USERNAME, cityTest, EMAIL, JOINDATE);
    Type typeTest = new Type(TYPE_ID, TYPE_NAME);
    Venue venueTest = new Venue(VENUE_ID, VENUE_NAME, typeTest, cityTest, ADDRESS, RATING, REVIEWS);
    when(reviewService.deleteReview(REVIEW_ID)).thenReturn(new ReviewDto(REVIEW_ID, userTest, venueTest,
                                                                         CREATIONDATE, RATING, TEXT));
    mockMvc.perform(delete("/reviews/" + REVIEW_ID))
           .andExpect(status().isNoContent());
  }

  @Test
  public void deleteReview_requestedResponse_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    User userTest = new User(USER_ID, USERNAME, cityTest, EMAIL, JOINDATE);
    Type typeTest = new Type(TYPE_ID, TYPE_NAME);
    Venue venueTest = new Venue(VENUE_ID, VENUE_NAME, typeTest, cityTest, ADDRESS, RATING, REVIEWS);
    when(reviewService.deleteReview(REVIEW_ID)).thenReturn(new ReviewDto(REVIEW_ID, userTest, venueTest,
                                                                         CREATIONDATE, RATING, TEXT));
    mockMvc.perform(delete("/reviews/" + REVIEW_ID)
                      .queryParam("returnOld", "true"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(REVIEW_ID))
           .andExpect(jsonPath("$.user.id").value(USER_ID))
           .andExpect(jsonPath("$.user.username").value(USERNAME))
           .andExpect(jsonPath("$.venue.id").value(VENUE_ID))
           .andExpect(jsonPath("$.venue.name").value(VENUE_NAME))
           .andExpect(jsonPath("$.creationDate[0]").value("2023"))
           .andExpect(jsonPath("$.creationDate[1]").value("5"))
           .andExpect(jsonPath("$.creationDate[2]").value("1"))
           .andExpect(jsonPath("$.rating").value(RATING))
           .andExpect(jsonPath("$.text").value(TEXT));
  }
}
