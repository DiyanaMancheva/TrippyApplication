package com.diyanamancheva.service;

import com.diyanamancheva.controller.request.venue.VenueUpdateRequest;
import com.diyanamancheva.dto.mapper.VenueMapper;
import com.diyanamancheva.model.City;
import com.diyanamancheva.model.Venue;
import com.diyanamancheva.repository.VenueRepository;
import com.diyanamancheva.model.Type;
import com.diyanamancheva.dto.venue.VenueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {
  private final VenueRepository venueRepository;
  private final VenueMapper venueMapper;
  private final TypeService typeService;
  private final CityService cityService;

  @Autowired
  public VenueService(
    VenueRepository venueRepository, VenueMapper venueMapper,
    TypeService typeService, CityService cityService){
    this.venueRepository = venueRepository;
    this.venueMapper = venueMapper;
    this.typeService = typeService;
    this.cityService = cityService;
  }

  public List<VenueDto> getAllVenues(){
    List<Venue> venues = venueRepository.readAllVenues();
    List<VenueDto> venueDtos = venueMapper.mapVenuesToDtos(venues);

    return venueDtos;
  }

  public Venue getVenueById(int id){
    Venue venue = venueRepository.readVenueById(id);

    return  venue;
  }

  public List<Venue> getVenuesByType(int typeId){
    Type type = typeService.getTypeById(typeId);
    List<Venue> venues = venueRepository.readVenuesByType(typeId);

    return  venues;
  }

  public List<Venue> getVenuesByCity(int cityId){
    City city = cityService.getCityById(cityId);
    List<Venue> venues = venueRepository.readVenuesByCity(cityId);

    return  venues;
  }

  public Venue addVenue(String name, int typeId, int cityId,
                          String address, float rating, int reviews) {
    venueRepository.readVenuesByNameAndCity(name, cityId);
    Type type = typeService.getTypeById(typeId);
    City city = cityService.getCityById(cityId);

    Venue venue = new Venue(name, type, city, address, rating, reviews);
    venueRepository.addVenue(venue);

    return venue;
  }

  public VenueDto updateVenue(int id, VenueUpdateRequest venueRequest){
    Venue venue = getVenueById(id);

    Venue venueNew = new Venue(id, venue.getName(), venue.getType(),
                            venue.getCity(), venueRequest.getAddress(), venue.getRating(), venue.getReviews());

    venueRepository.updateVenue(venueNew);

    VenueDto venueDto = new VenueDto(venue.getId(), venue.getName(), venue.getType(),
                                  venue.getCity(), venueNew.getAddress(), venue.getRating(), venue.getReviews());

    return venueDto;
  }

  public void updateVenueRatingAndReviews(int id, float rating){
    Venue venue = getVenueById(id);

    float ratingCurrent = venue.getRating();
    float ratingNew = (ratingCurrent + rating) / 2.0f;
    int reviewsCurrent = venue.getReviews();
    int reviewsNew = reviewsCurrent + 1;
    Venue venueNew = new Venue(id, venue.getName(), venue.getType(),
                               venue.getCity(), venue.getAddress(), ratingNew, reviewsNew);

    venueRepository.updateVenueRatingAndReviews(venueNew);
  }

  public VenueDto deleteVenue(int id){
    Venue venue = getVenueById(id);
    venueRepository.deleteVenue(id);
    VenueDto venueDto = new VenueDto(venue.getId(), venue.getName(), venue.getType(),
                                     venue.getCity(), venue.getAddress(), venue.getRating(), venue.getReviews());
    return venueDto;
  }
}
