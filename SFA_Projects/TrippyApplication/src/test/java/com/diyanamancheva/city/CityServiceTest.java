package com.diyanamancheva.city;

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
  private CityAccessor cityAccessor;

  @InjectMocks
  private CityService cityService;

  public static final int CITY_ID = 1;
  public static final String CITY_NAME = "Test";

  @Test
  public void updateCity_success(){
    when(cityService.getCityById(CITY_ID)).thenReturn(new City(CITY_ID, CITY_NAME));
    CityRequest cityRequest = new CityRequest("New Name");

    CityDto result = cityService.updateCity(CITY_ID, cityRequest);

    verify(cityAccessor, times(1)).readCityById(CITY_ID);
    assertEquals(CITY_NAME, result.getName());
  }

  @Test
  public void deleteCity_success() {
    when(cityService.getCityById(CITY_ID)).thenReturn(new City(CITY_ID, CITY_NAME));

    cityService.deleteCity(CITY_ID);

    verify(cityAccessor, times(1)).readCityById(CITY_ID);
    verify(cityAccessor, times(1)).deleteCity(CITY_ID);
  }

}
