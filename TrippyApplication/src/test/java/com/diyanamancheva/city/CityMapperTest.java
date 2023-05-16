package com.diyanamancheva.city;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityMapperTest {
  private static final int CITY_ID = 1;
  private static final String CITY_NAME = "TestCity";

  @Mock
  private ResultSet resultSet;

  @InjectMocks
  private CityMapper cityMapper;

  @Test
  public void mapResultSetToCities_noExceptions_success() throws SQLException {
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(resultSet.getInt(anyInt())).thenReturn(CITY_ID);
    when(resultSet.getString(anyInt())).thenReturn(CITY_NAME);

    List<City> cityList = cityMapper.mapResultSetToCities(resultSet);

    assertEquals(1, cityList.size());
    assertEquals(CITY_NAME, cityList.get(0).getName());
  }

  @Test(expected = RuntimeException.class)
  public void mapResultSetToCities_resultSetSQLException_success() throws SQLException {
    when(resultSet.next()).thenThrow(new SQLException());

    cityMapper.mapResultSetToCities(resultSet);
  }

  @Test
  public void mapCitiesToDtos_noExceptions_success() {
    List<City> input = Collections.singletonList(new City(CITY_ID, CITY_NAME));

    List<CityDto> result = cityMapper.mapCitiesToDtos(input);

    assertEquals(CITY_NAME, result.get(0).getName());
  }
}
