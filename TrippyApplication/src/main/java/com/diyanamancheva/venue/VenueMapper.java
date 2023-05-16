package com.diyanamancheva.venue;

import com.diyanamancheva.city.City;
import com.diyanamancheva.city.CityService;
import com.diyanamancheva.type.Type;
import com.diyanamancheva.type.TypeService;
import com.diyanamancheva.exception.DatabaseConnectivityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class VenueMapper {

  private static final Logger log = LoggerFactory.getLogger(VenueAccessor.class);

  private TypeService typeService;
  private CityService cityService;

  @Autowired
  public VenueMapper(TypeService typeService, CityService cityService){
    this.typeService = typeService;
    this.cityService = cityService;
  }

  public List<Venue> mapResultSetToVenues (ResultSet venuesResultSet){
    List<Venue> venues = new ArrayList<>();
    try(venuesResultSet){
      while (venuesResultSet.next()){
        int id = venuesResultSet.getInt(1);
        int typeId = venuesResultSet.getInt(2);
        int cityId = venuesResultSet.getInt(3);
        String name = venuesResultSet.getString(4);
        String address = venuesResultSet.getString(5);
        float rating = venuesResultSet.getFloat(6);
        int reviews = venuesResultSet.getInt(7);

        Type type = typeService.getTypeById(typeId);
        City city = cityService.getCityById(cityId);

        Venue venue = new Venue(id, name, type, city, address, rating, reviews);

        venues.add(venue);
      }
    }catch (SQLException e){
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return venues;
  }

  public List<VenueDto> mapVenuesToDtos(List<Venue> venues){
    List<VenueDto> venueDtos = new ArrayList<>();

    for (Venue venue : venues){
      VenueDto venueDto = new VenueDto(venue.getId(), venue.getName(), venue.getType(),
                                       venue.getCity(), venue.getAddress(), venue.getRating(),
                                       venue.getReviews());
      venueDtos.add(venueDto);
    }

    return venueDtos;
  }
}
