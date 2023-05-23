package com.diyanamancheva.service;

import com.diyanamancheva.exception.EntityNotFoundException;
import com.diyanamancheva.controller.request.city.CityRequest;
import com.diyanamancheva.dto.city.CityDto;
import com.diyanamancheva.dto.mapper.CityMapper;
import com.diyanamancheva.model.City;
import com.diyanamancheva.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
  private final CityRepository cityRepository;
  private final CityMapper cityMapper;

  @Autowired
  public CityService(CityRepository cityRepository, CityMapper cityMapper){
    this.cityRepository = cityRepository;
    this.cityMapper = cityMapper;
  }

  public List<CityDto> getAllCities(){
    Iterable<City> cities = cityRepository.findAll();
    List<CityDto> cityDtos = cityMapper.mapCitiesToDtos(cities);

    return cityDtos;
  }

  public City getCityById(int id){
    City city = cityRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException(String.format("City with id %d not found", id)));

    return  city;
  }

  public City addCity(CityRequest cityRequest){
    City cityNew = cityMapper.mapCityRequestToCity(cityRequest);
    cityNew = cityRepository.save(cityNew);

    return cityNew;
  }

  public CityDto updateCity(int id, CityRequest cityRequest){
    City city = getCityById(id);
    City cityOld = new City(city.getId(), city.getName());

    city.setName(cityRequest.getName());
    cityRepository.save(city);

    CityDto cityDto = cityMapper.mapCityToDto(cityOld);
    return cityDto;
  }

  public CityDto deleteCity(int id){
    City city = getCityById(id);
    cityRepository.deleteById(id);
    CityDto cityDto = new CityDto(city.getId(), city.getName());

    return cityDto;
  }
}
