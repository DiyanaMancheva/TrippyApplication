package com.diyanamancheva.venue;

import com.diyanamancheva.user.UserDto;
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
import java.util.ArrayList;
import java.util.List;

@RestController
public class VenueController {
  private VenueService venueService;

  @Autowired
  public VenueController(VenueService venueService){
    this.venueService = venueService;
  }

  @GetMapping("/venues")
  public ResponseEntity<List<VenueDto>> getAllVenues(){
    List<VenueDto> venueDtos = venueService.getAllVenues();

    return ResponseEntity.ok(venueDtos);
  }

  @GetMapping("/venues/{id}")
  public ResponseEntity<VenueDto> getVenueById(@PathVariable int id){
    Venue venue = venueService.getVenueById(id);
    VenueDto venueDto = new VenueDto(venue.getId(), venue.getName(), venue.getType(),
                                     venue.getCity(), venue.getAddress(), venue.getRating(), venue.getReviews());

    return ResponseEntity.ok(venueDto);
  }

  @GetMapping("/types/{typeId}/venues")
  public ResponseEntity<List<VenueByTypeDto>> getVenuesByType(@PathVariable int typeId){
    List<Venue> venues = venueService.getVenuesByType(typeId);
    List<VenueByTypeDto> venueByTypeDtos = new ArrayList<>();

    for(Venue venue : venues){
      VenueByTypeDto venueByTypeDto = new VenueByTypeDto(venue.getId(), venue.getName(), venue.getCity().getName(),
                                                    venue.getAddress(), venue.getRating(), venue.getReviews());
      venueByTypeDtos.add(venueByTypeDto);
    }

    return ResponseEntity.ok(venueByTypeDtos);
  }

  @GetMapping("/cities/{cityId}/venues")
  public ResponseEntity<List<VenueByCityDto>> getVenuesByCity(@PathVariable int cityId){
    List<Venue> venues = venueService.getVenuesByCity(cityId);
    List<VenueByCityDto> venueByCityDtos = new ArrayList<>();

    for(Venue venue : venues){
      VenueByCityDto venueByCityDto = new VenueByCityDto(venue.getId(), venue.getName(), venue.getType().getName(),
                                                         venue.getAddress(), venue.getRating(), venue.getReviews());
      venueByCityDtos.add(venueByCityDto);
    }

    return ResponseEntity.ok(venueByCityDtos);
  }

  @PostMapping("/venues")
  public ResponseEntity<Void> createVenue(@RequestBody @Valid VenueRequest venueRequest){
    Venue venue = venueService.addVenue(venueRequest.getName(), venueRequest.getType(), venueRequest.getCity(),
                                        venueRequest.getAddress(), venueRequest.getRating(), venueRequest.getReviews());

    URI location = UriComponentsBuilder.fromUriString("/venues/{id}")
                                       .buildAndExpand(venue.getId()).toUri();

    return ResponseEntity.created(location).build();
  }

  @PutMapping("/venues/{id}")
  public ResponseEntity<VenueDto> updateVenue (@RequestBody @Valid VenueUpdateRequest venueRequest,
                                             @PathVariable int id,
                                             @RequestParam(required = false) boolean returnOld){

    VenueDto venueDto = venueService.updateVenue(id, venueRequest);
    if (returnOld){
      return ResponseEntity.ok(venueDto);
    }else{
      return ResponseEntity.noContent().build();
    }
  }

  @DeleteMapping("/venues/{id}")
  public ResponseEntity<VenueDto> deleteVenue(@PathVariable int id,
                                            @RequestParam(required = false) boolean returnOld){
    VenueDto venueDto = venueService.deleteVenue(id);

    if(returnOld){
      return ResponseEntity.ok(venueDto);
    } else {
      return ResponseEntity.noContent().build();
    }
  }
}
