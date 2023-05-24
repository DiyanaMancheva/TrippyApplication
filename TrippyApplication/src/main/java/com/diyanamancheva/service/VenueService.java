package com.diyanamancheva.service;

import com.diyanamancheva.controller.request.venue.VenueRequest;
import com.diyanamancheva.controller.request.venue.VenueUpdateRequest;
import com.diyanamancheva.dto.mapper.VenueMapper;
import com.diyanamancheva.exception.EntityNotFoundException;
import com.diyanamancheva.model.Venue;
import com.diyanamancheva.repository.VenueRepository;
import com.diyanamancheva.dto.venue.VenueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VenueService {
  private final VenueRepository venueRepository;
  private final VenueMapper venueMapper;

  @Autowired
  public VenueService(
    VenueRepository venueRepository, VenueMapper venueMapper){
    this.venueRepository = venueRepository;
    this.venueMapper = venueMapper;
  }

  public List<VenueDto> getAllVenues(){
    Iterable<Venue> venues = venueRepository.findAll();
    List<VenueDto> venueDtos = venueMapper.mapVenuesToDtos(venues);

    return venueDtos;
  }

  public Venue getVenueById(int id){
    Venue venue = venueRepository.findById(id)
                                 .orElseThrow(() -> new EntityNotFoundException(String.format("Venue with id %d not found", id)));

    return  venue;
  }

  public List<Venue> getVenuesByType(int typeId){
    List<Venue> venues = new ArrayList<>();
    venueRepository.findAll().forEach(venue -> {
      if(venue.getType().getId() == typeId){
        venues.add(venue);
      }
    });

    return venues;
  }

  public List<Venue> getVenuesByCity(int cityId){
    List<Venue> venues = new ArrayList<>();
    venueRepository.findAll().forEach(venue -> {
      if(venue.getCity().getId() == cityId){
        venues.add(venue);
      }
    });

    return venues;
  }

  public Venue addVenue(VenueRequest venueRequest) {
    Venue venueNew = venueMapper.mapVenueRequestToVenue(venueRequest);
    venueNew = venueRepository.save(venueNew);

    return venueNew;
  }

  public VenueDto updateVenue(int id, VenueUpdateRequest venueRequest){
    Venue venue = getVenueById(id);

    Venue venueOld = new Venue(venue.getId(), venue.getName(), venue.getType(),
                            venue.getCity(), venue.getAddress(), venue.getRating(), venue.getReviews());

    venue.setAddress(venueRequest.getAddress());
    venueRepository.save(venue);

    VenueDto venueDto = venueMapper.mapVenueToDto(venueOld);

    return venueDto;
  }

  public VenueDto deleteVenue(int id){
    Venue venue = getVenueById(id);
    venueRepository.deleteById(id);
    VenueDto venueDto = new VenueDto(venue.getId(), venue.getName(), venue.getType(),
                                     venue.getCity(), venue.getAddress(), venue.getRating(), venue.getReviews());
    return venueDto;
  }
}
