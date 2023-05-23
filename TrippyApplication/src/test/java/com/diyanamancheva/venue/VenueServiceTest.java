package com.diyanamancheva.venue;

import com.diyanamancheva.model.City;
import com.diyanamancheva.model.Type;
import com.diyanamancheva.model.Venue;
import com.diyanamancheva.repository.VenueRepository;
import com.diyanamancheva.service.VenueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VenueServiceTest {

  @Mock
  private VenueRepository venueRepository;

  @InjectMocks
  private VenueService venueService;

  private static final int VENUE_ID = 1;
  private static final String VENUE_NAME = "Test";
  private static final int CITY_ID = 1;
  private static final String CITY_NAME = "TestCity";
  private static final int TYPE_ID = 1;
  private static final String TYPE_NAME = "TestType";
  private static final String ADDRESS = "1 Test Str.";
  private static final int RATING = 1;
  private static final int REVIEWS = 0;

  @Test
  public void deleteVenue_success() {
    City cityTest = new City(CITY_ID, CITY_NAME);
    Type typeTest = new Type(TYPE_ID, TYPE_NAME);
    when(venueService.getVenueById(VENUE_ID)).thenReturn(new Venue(VENUE_ID, VENUE_NAME, typeTest, cityTest,
                                                                   ADDRESS, RATING, REVIEWS));
    venueService.deleteVenue(VENUE_ID);

    verify(venueRepository, times(1)).readVenueById(VENUE_ID);
    verify(venueRepository, times(1)).deleteVenue(VENUE_ID);
  }

}
