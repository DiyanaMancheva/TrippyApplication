package com.diyanamancheva.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
