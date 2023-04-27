package com.diyanamancheva.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
  private CityAccessor cityAccessor;
  private CityMapper cityMapper;

  @Autowired
  public CityService(CityAccessor cityAccessor, CityMapper cityMapper){
    this.cityAccessor = cityAccessor;
    this.cityMapper = cityMapper;
  }

  public List<CityDto> getAllCities(){
    List<City> cities = cityAccessor.readAllCities();
    List<CityDto> cityDtos = cityMapper.mapCitiesToDtos(cities);

    return cityDtos;
  }
}
