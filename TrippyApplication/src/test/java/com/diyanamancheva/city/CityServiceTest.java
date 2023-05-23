package com.diyanamancheva.city;

import com.diyanamancheva.controller.request.city.CityRequest;
import com.diyanamancheva.dto.city.CityDto;
import com.diyanamancheva.model.City;
import com.diyanamancheva.repository.CityRepository;
import com.diyanamancheva.service.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

  @Mock
  private CityRepository cityRepository;

  @InjectMocks
  private CityService cityService;

  private static final int CITY_ID = 1;
  private static final String CITY_NAME = "Test";

  @Test
  public void updateCity_success(){
    when(cityService.getCityById(CITY_ID)).thenReturn(new City(CITY_ID, CITY_NAME));
    CityRequest cityRequest = new CityRequest("New Name");

    CityDto result = cityService.updateCity(CITY_ID, cityRequest);

    verify(cityRepository, times(1)).readCityById(CITY_ID);
    assertEquals(CITY_NAME, result.getName());
  }

  @Test
  public void deleteCity_success() {
    when(cityService.getCityById(CITY_ID)).thenReturn(new City(CITY_ID, CITY_NAME));

    cityService.deleteCity(CITY_ID);

    verify(cityRepository, times(1)).readCityById(CITY_ID);
    verify(cityRepository, times(1)).deleteCity(CITY_ID);
  }

}
