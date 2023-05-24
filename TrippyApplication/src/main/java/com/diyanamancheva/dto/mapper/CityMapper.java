package com.diyanamancheva.dto.mapper;

import com.diyanamancheva.controller.request.city.CityRequest;
import com.diyanamancheva.dto.city.CityDto;
import com.diyanamancheva.model.City;
import com.diyanamancheva.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CityMapper {

  private static final Logger log = LoggerFactory.getLogger(CityRepository.class);

  public City mapCityRequestToCity (CityRequest citiesRequest){
    return new City(citiesRequest.getName());
  }

  public List<CityDto> mapCitiesToDtos(Iterable<City> cities){
    ArrayList<CityDto> cityDtos = new ArrayList<>();

    cities.forEach(city -> cityDtos.add(mapCityToDto(city)));
    return cityDtos;
  }

  public CityDto mapCityToDto(City city) {
    return new CityDto(city.getId(), city.getName());
  }
}
