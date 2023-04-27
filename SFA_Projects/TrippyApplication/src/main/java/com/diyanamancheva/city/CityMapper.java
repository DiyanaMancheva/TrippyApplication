package com.diyanamancheva.city;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CityMapper {

  public List<City> mapResultSetToCities (ResultSet citiesResultSet){
    List<City> cities = new ArrayList<>();
    try(citiesResultSet){
      while (citiesResultSet.next()){
        int id = citiesResultSet.getInt(1);
        String name = citiesResultSet.getString(2);
        City city = new City(id, name);
        cities.add(city);
      }
    }catch (SQLException e){
      throw new RuntimeException(e);
    }

    return cities;
  }

  public List<CityDto> mapCitiesToDtos(List<City> cities){
    List<CityDto> cityDtos = new ArrayList<>();

    for (City city : cities){
      CityDto cityDto = new CityDto(city.getId(), city.getName());
      cityDtos.add(cityDto);
    }

    return cityDtos;
  }
}
