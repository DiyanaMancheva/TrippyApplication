package com.diyanamancheva.controller;

import com.diyanamancheva.model.City;
import com.diyanamancheva.dto.city.CityDto;
import com.diyanamancheva.controller.request.city.CityRequest;
import com.diyanamancheva.service.CityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class CityController {
  private CityService cityService;

  @Autowired
  public CityController(CityService cityService){

    this.cityService = cityService;
  }

  @GetMapping("/cities")
  public ResponseEntity<List<CityDto>> printAllCities(){
    List<CityDto> cityDtos = cityService.getAllCities();

    return ResponseEntity.ok(cityDtos);
  }

  @GetMapping("/cities/{id}")
  public ResponseEntity<CityDto> getCityById(@PathVariable int id){
    City city = cityService.getCityById(id);
    CityDto cityDto = new CityDto(city.getId(), city.getName());

    return ResponseEntity.ok(cityDto);
  }

  @PostMapping("/cities")
  public ResponseEntity<Void> createCity(@RequestBody @Valid CityRequest cityRequest){
    City city = cityService.addCity(cityRequest.getName());
    URI location = UriComponentsBuilder.fromUriString("/cities/{id}")
                                       .buildAndExpand(city.getId()).toUri();

    return ResponseEntity.created(location).build();
  }

  @PutMapping("/cities/{id}")
  public ResponseEntity<CityDto> updateCity(@RequestBody @Valid CityRequest cityRequest,
                                            @PathVariable int id,
                                            @RequestParam(required = false) boolean returnOld){
    CityDto cityDto = cityService.updateCity(id, cityRequest);

    if(returnOld){
      return ResponseEntity.ok(cityDto);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  @DeleteMapping("/cities/{id}")
  public ResponseEntity<CityDto> deleteCity(@PathVariable int id,
                                            @RequestParam(required = false) boolean returnOld){
    CityDto cityDto = cityService.deleteCity(id);

    if(returnOld){
      return ResponseEntity.ok(cityDto);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

}
