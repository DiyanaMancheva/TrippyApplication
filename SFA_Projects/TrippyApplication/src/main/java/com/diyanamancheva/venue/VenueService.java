package com.diyanamancheva.venue;

import com.diyanamancheva.city.City;
import com.diyanamancheva.city.CityService;
import com.diyanamancheva.type.Type;
import com.diyanamancheva.type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {
  private VenueAccessor venueAccessor;
  private VenueMapper venueMapper;
  private TypeService typeService;
  private CityService cityService;

  @Autowired
  public VenueService(VenueAccessor venueAccessor, VenueMapper venueMapper,
                      TypeService typeService, CityService cityService){
    this.venueAccessor = venueAccessor;
    this.venueMapper = venueMapper;
    this.typeService = typeService;
    this.cityService = cityService;
  }

  public List<VenueDto> getAllVenues(){
    List<Venue> venues = venueAccessor.readAllVenues();
    List<VenueDto> venueDtos = venueMapper.mapVenuesToDtos(venues);

    return venueDtos;
  }

  public Venue getVenueById(int id){
    Venue venue = venueAccessor.readVenueById(id);

    return  venue;
  }

  public Venue addVenue(String name, int typeId, int cityId,
                          String address, float rating) {
    Type type = typeService.getTypeById(typeId);
    City city = cityService.getCityById(cityId);

    Venue venue = new Venue(name, type, city, address, rating);
    venueAccessor.addVenue(venue);

    return venue;
  }
}
