package com.diyanamancheva.venue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {
  private VenueAccessor venueAccessor;
  private VenueMapper venueMapper;

  @Autowired
  public VenueService(VenueAccessor venueAccessor, VenueMapper venueMapper){
    this.venueAccessor = venueAccessor;
    this.venueMapper = venueMapper;
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
}
