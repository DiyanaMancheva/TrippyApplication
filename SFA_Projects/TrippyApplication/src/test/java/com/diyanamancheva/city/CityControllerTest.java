package com.diyanamancheva.city;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class CityControllerTest {
  private static final int CITY_ID = 1;
  private static final String CITY_NAME = "Test";

  private MockMvc mockMvc;

  @Mock
  private CityService cityService;

  @InjectMocks
  private CityController cityController;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(cityController).build();
  }

  @Test
  public void getAllCities_singleCity_success() throws Exception {
    when(cityService.getAllCities()).thenReturn(Collections.singletonList(new CityDto(CITY_ID, CITY_NAME)));

    mockMvc.perform(get("/cities"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(CITY_ID))
           .andExpect(jsonPath("$[0].name").value(CITY_NAME));
  }

  @Test
  public void getAllCities_emptyList_success() throws Exception {
    when(cityService.getAllCities()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/cities"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").exists())
           .andExpect(jsonPath("$[0]").doesNotExist());
  }

  @Test
  public void getCityById_success() throws Exception {
    when(cityService.getCityById(CITY_ID)).thenReturn(new City(CITY_ID, CITY_NAME));

    mockMvc.perform(get("/cities/" + CITY_ID))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(CITY_ID))
           .andExpect(jsonPath("$.name").value(CITY_NAME));
  }

  @Test
  public void createCity_success() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(new CityRequest(CITY_NAME));

    when(cityService.addCity(any())).thenReturn(new City(CITY_ID, CITY_NAME));
    mockMvc.perform(post("/cities")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/cities/1"));
  }

  @Test
  public void updateCity_noResponse_success() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(new CityRequest(CITY_NAME));

    when(cityService.updateCity(eq(CITY_ID), any())).thenReturn(new CityDto(CITY_ID, CITY_NAME));

    mockMvc.perform(put("/cities/" + CITY_ID)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isNoContent());
  }

  @Test
  public void updateCity_requestedResponse_success() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(new CityRequest(CITY_NAME));

    when(cityService.updateCity(eq(CITY_ID), any())).thenReturn(new CityDto(CITY_ID, CITY_NAME));

    mockMvc.perform(put("/cities/" + CITY_ID)
                      .queryParam("returnOld", "true")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(CITY_ID))
           .andExpect(jsonPath("$.name").value(CITY_NAME));
  }

  @Test
  public void deleteCity_noResponse_success() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(new CityRequest(CITY_NAME));

    when(cityService.updateCity(eq(CITY_ID), any())).thenReturn(new CityDto(CITY_ID, CITY_NAME));

    mockMvc.perform(delete("/cities/" + CITY_ID))
           .andExpect(status().isNoContent());
  }

  @Test
  public void deleteCity_requestedResponse_success() throws Exception {

    when(cityService.updateCity(eq(CITY_ID), any())).thenReturn(new CityDto(CITY_ID, CITY_NAME));

    mockMvc.perform(delete("/cities/" + CITY_ID)
                      .queryParam("returnOld", "true"))
           .andExpect(status().isOk());
  }
}
