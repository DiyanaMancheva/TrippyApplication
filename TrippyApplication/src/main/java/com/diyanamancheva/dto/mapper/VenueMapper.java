package com.diyanamancheva.dto.mapper;

import com.diyanamancheva.controller.request.venue.VenueRequest;
import com.diyanamancheva.model.City;
import com.diyanamancheva.model.Venue;
import com.diyanamancheva.repository.VenueRepository;
import com.diyanamancheva.service.CityService;
import com.diyanamancheva.model.Type;
import com.diyanamancheva.service.TypeService;
import com.diyanamancheva.dto.venue.VenueDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VenueMapper {

  private static final Logger log = LoggerFactory.getLogger(VenueRepository.class);

  private final TypeService typeService;
  private final CityService cityService;

  @Autowired
  public VenueMapper(TypeService typeService, CityService cityService){
    this.typeService = typeService;
    this.cityService = cityService;
  }

  public Venue mapVenueRequestToVenue (VenueRequest venueRequest){

    Type type = typeService.getTypeById(venueRequest.getType());
    City city = cityService.getCityById(venueRequest.getCity());

    return new Venue(venueRequest.getName(), type, city, venueRequest.getAddress(),
                         venueRequest.getRating(), venueRequest.getReviews());

  }

  public List<VenueDto> mapVenuesToDtos(Iterable<Venue> venues){
    List<VenueDto> venueDtos = new ArrayList<>();

    venues.forEach(venue -> venueDtos.add(mapVenueToDto(venue)));

    return venueDtos;
  }

  public VenueDto mapVenueToDto(Venue venue) {

    return new VenueDto(venue.getId(), venue.getName(), venue.getType(),
                         venue.getCity(), venue.getAddress(), venue.getRating(), venue.getReviews());
  }
}
