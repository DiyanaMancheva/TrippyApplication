package com.diyanamancheva.dto.mapper;

import com.diyanamancheva.controller.request.user.UserRequest;
import com.diyanamancheva.dto.user.UserDto;
import com.diyanamancheva.model.City;
import com.diyanamancheva.model.User;
import com.diyanamancheva.repository.UserRepository;
import com.diyanamancheva.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

  private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
  private final CityService cityService;

  @Autowired
  public UserMapper(CityService cityService){
    this.cityService = cityService;
  }

  public User mapUserRequestToUser (UserRequest usersRequest){
        City city = cityService.getCityById(usersRequest.getCityId());

        return new User(usersRequest.getUsername(), city,
                        usersRequest.getEmail(), usersRequest.getJoinDate());
  }

  public List<UserDto> mapUsersToDtos(Iterable<User> users){
    ArrayList<UserDto> userDtos = new ArrayList<>();

    users.forEach(user -> userDtos.add(mapUserToDto(user)));

    return userDtos;
  }

  public UserDto mapUserToDto(User user) {

    return new UserDto(user.getId(), user.getUsername(), user.getCity(),
                       user.getEmail(), user.getJoinDate());
  }
}
