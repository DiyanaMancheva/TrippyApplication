package com.diyanamancheva.user;

import com.diyanamancheva.city.City;
import com.diyanamancheva.city.CityService;
import com.diyanamancheva.exception.DatabaseConnectivityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

  private static final Logger log = LoggerFactory.getLogger(UserAccessor.class);
  private CityService cityService;

  @Autowired
  public UserMapper(CityService cityService){
    this.cityService = cityService;
  }

  public List<User> mapResultSetToUsers (ResultSet usersResultSet){
    List<User> users = new ArrayList<>();
    try(usersResultSet){
      while (usersResultSet.next()){
        int id = usersResultSet.getInt(1);
        String username = usersResultSet.getString(2);
        int cityId = usersResultSet.getInt(3);
        String email = usersResultSet.getString(4);
        String joinDateString = usersResultSet.getString(5);

        City city = cityService.getCityById(cityId);
        LocalDate joinDate = LocalDate.parse(joinDateString);

        User user = new User(id, username, city, email, joinDate);

        users.add(user);
      }
    }catch (SQLException e){
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return users;
  }

  public List<UserDto> mapUsersToDtos(List<User> users){
    List<UserDto> userDtos = new ArrayList<>();

    for (User user : users){
      UserDto userDto = new UserDto(user.getId(), user.getUsername(),
                                    user.getCity(), user.getEmail(),
                                    user.getJoinDate());
      userDtos.add(userDto);
    }

    return userDtos;
  }
}
