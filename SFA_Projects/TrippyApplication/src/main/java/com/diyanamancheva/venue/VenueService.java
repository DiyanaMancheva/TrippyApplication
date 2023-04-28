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
                          String address, float rating, int reviews) {
    venueAccessor.readVenuesByNameAndCity(name, cityId);
    Type type = typeService.getTypeById(typeId);
    City city = cityService.getCityById(cityId);

    Venue venue = new Venue(name, type, city, address, rating, reviews);
    venueAccessor.addVenue(venue);

    return venue;
  }

  public VenueDto updateVenue(int id, VenueUpdateRequest venueRequest){
    //venueAccessor.readVenuesByNameAndCity(venueRequest.getName(), venueRequest.getCity());
    Venue venue = getVenueById(id);

    Venue venueNew = new Venue(id, venue.getName(), venue.getType(),
                            venue.getCity(), venueRequest.getAddress(), venue.getRating(), venue.getReviews());

    venueAccessor.updateVenue(venueNew);

    VenueDto venueDto = new VenueDto(venue.getId(), venue.getName(), venue.getType(),
                                  venue.getCity(), venueNew.getAddress(), venue.getRating(), venue.getReviews());

    return venueDto;
  }

  public VenueDto deleteVenue(int id){
    Venue venue = getVenueById(id);
    venueAccessor.deleteVenue(id);
    VenueDto venueDto = new VenueDto(venue.getId(), venue.getName(), venue.getType(),
                                     venue.getCity(), venue.getAddress(), venue.getRating(), venue.getReviews());
    return venueDto;
  }
}
