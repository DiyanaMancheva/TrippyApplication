package com.diyanamancheva.city;

import com.diyanamancheva.exception.DatabaseConnectivityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CityMapper {

  private static final Logger log = LoggerFactory.getLogger(CityAccessor.class);

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
      log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
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
