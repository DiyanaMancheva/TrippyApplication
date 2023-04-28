package com.diyanamancheva.venue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
}
