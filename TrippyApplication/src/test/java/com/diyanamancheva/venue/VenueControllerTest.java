package com.diyanamancheva.venue;

import com.diyanamancheva.controller.VenueController;
import com.diyanamancheva.controller.request.venue.VenueUpdateRequest;
import com.diyanamancheva.dto.venue.VenueDto;
import com.diyanamancheva.model.City;
import com.diyanamancheva.model.Type;
import com.diyanamancheva.model.Venue;
import com.diyanamancheva.service.VenueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class VenueControllerTest {
  private static final int VENUE_ID = 1;
  private static final String VENUE_NAME = "Test";
  private static final int CITY_ID = 1;
  private static final String CITY_NAME = "TestCity";
  private static final int TYPE_ID = 1;
  private static final String TYPE_NAME = "TestType";
  private static final String ADDRESS = "1 Test Str.";
  private static final float RATING = 1;
  private static final int REVIEWS = 0;

  private MockMvc mockMvc;

  @Mock
  private VenueService venueService;

  @InjectMocks
  private VenueController venueController;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(venueController).build();
  }

  @Test
  public void getAllVenues_singleVenue_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    Type typeTest = new Type(TYPE_ID, TYPE_NAME);
    when(venueService.getAllVenues()).thenReturn(Collections.singletonList(new VenueDto(VENUE_ID, VENUE_NAME, typeTest,
                                                                                        cityTest, ADDRESS, RATING, REVIEWS)));
    mockMvc.perform(get("/venues"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(VENUE_ID))
           .andExpect(jsonPath("$[0].name").value(VENUE_NAME))
           .andExpect(jsonPath("$[0].type.id").value(TYPE_ID))
           .andExpect(jsonPath("$[0].type.name").value(TYPE_NAME))
           .andExpect(jsonPath("$[0].city.id").value(CITY_ID))
           .andExpect(jsonPath("$[0].city.name").value(CITY_NAME))
           .andExpect(jsonPath("$[0].address").value(ADDRESS))
           .andExpect(jsonPath("$[0].rating").value(RATING))
           .andExpect(jsonPath("$[0].reviews").value(REVIEWS));
  }

  @Test
  public void getAllVenues_emptyList_success() throws Exception {
    when(venueService.getAllVenues()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/venues"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").exists())
           .andExpect(jsonPath("$[0]").doesNotExist());
  }

  @Test
  public void getVenueById_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    Type typeTest = new Type(TYPE_ID, TYPE_NAME);
    when(venueService.getVenueById(VENUE_ID)).thenReturn(new Venue(VENUE_ID, VENUE_NAME, typeTest, cityTest,
                                                                   ADDRESS, RATING, REVIEWS));

    mockMvc.perform(get("/venues/" + VENUE_ID))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(VENUE_ID))
           .andExpect(jsonPath("$.name").value(VENUE_NAME))
           .andExpect(jsonPath("$.type.id").value(TYPE_ID))
           .andExpect(jsonPath("$.type.name").value(TYPE_NAME))
           .andExpect(jsonPath("$.city.id").value(CITY_ID))
           .andExpect(jsonPath("$.city.name").value(CITY_NAME))
           .andExpect(jsonPath("$.address").value(ADDRESS))
           .andExpect(jsonPath("$.rating").value(RATING))
           .andExpect(jsonPath("$.reviews").value(REVIEWS));
  }

  @Test
  public void updateVenue_noResponse_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    Type typeTest = new Type(TYPE_ID, TYPE_NAME);
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(new VenueUpdateRequest(ADDRESS));

    when(venueService.updateVenue(eq(VENUE_ID), any())).thenReturn(new VenueDto(VENUE_ID, VENUE_NAME, typeTest,
                                                                                cityTest, ADDRESS, RATING, REVIEWS));

    mockMvc.perform(put("/venues/" + VENUE_ID)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isNoContent());
  }

  @Test
  public void updateVenue_requestedResponse_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    Type typeTest = new Type(TYPE_ID, TYPE_NAME);
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(new VenueUpdateRequest(ADDRESS));

    when(venueService.updateVenue(eq(VENUE_ID), any())).thenReturn(new VenueDto(VENUE_ID, VENUE_NAME, typeTest,
                                                                                cityTest, ADDRESS, RATING, REVIEWS));

    mockMvc.perform(put("/venues/" + VENUE_ID)
                      .queryParam("returnOld", "true")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(VENUE_ID))
           .andExpect(jsonPath("$.name").value(VENUE_NAME))
           .andExpect(jsonPath("$.type.id").value(TYPE_ID))
           .andExpect(jsonPath("$.type.name").value(TYPE_NAME))
           .andExpect(jsonPath("$.city.id").value(CITY_ID))
           .andExpect(jsonPath("$.city.name").value(CITY_NAME))
           .andExpect(jsonPath("$.address").value(ADDRESS))
           .andExpect(jsonPath("$.rating").value(RATING))
           .andExpect(jsonPath("$.reviews").value(REVIEWS));
  }

  @Test
  public void deleteVenue_noResponse_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    Type typeTest = new Type(TYPE_ID, TYPE_NAME);
    when(venueService.deleteVenue(VENUE_ID)).thenReturn(new VenueDto(VENUE_ID, VENUE_NAME, typeTest,
                                                                     cityTest, ADDRESS, RATING, REVIEWS));
    mockMvc.perform(delete("/venues/" + VENUE_ID))
           .andExpect(status().isNoContent());
  }

  @Test
  public void deleteVenue_requestedResponse_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    Type typeTest = new Type(TYPE_ID, TYPE_NAME);
    when(venueService.deleteVenue(VENUE_ID)).thenReturn(new VenueDto(VENUE_ID, VENUE_NAME, typeTest,
                                                                     cityTest, ADDRESS, RATING, REVIEWS));
    mockMvc.perform(delete("/venues/" + VENUE_ID)
                      .queryParam("returnOld", "true"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(VENUE_ID))
           .andExpect(jsonPath("$.name").value(VENUE_NAME))
           .andExpect(jsonPath("$.type.id").value(TYPE_ID))
           .andExpect(jsonPath("$.type.name").value(TYPE_NAME))
           .andExpect(jsonPath("$.city.id").value(CITY_ID))
           .andExpect(jsonPath("$.city.name").value(CITY_NAME))
           .andExpect(jsonPath("$.address").value(ADDRESS))
           .andExpect(jsonPath("$.rating").value(RATING))
           .andExpect(jsonPath("$.reviews").value(REVIEWS));
  }
}
