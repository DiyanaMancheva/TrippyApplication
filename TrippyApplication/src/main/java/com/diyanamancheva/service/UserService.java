package com.diyanamancheva.service;

import com.diyanamancheva.controller.request.user.UserUpdateRequest;
import com.diyanamancheva.dto.mapper.UserMapper;
import com.diyanamancheva.dto.user.UserDto;
import com.diyanamancheva.model.City;
import com.diyanamancheva.model.User;
import com.diyanamancheva.repository.UserAccessor;
import com.diyanamancheva.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
  private final UserAccessor userAccessor;
  private final UserMapper userMapper;
  private final CityService cityService;

  @Autowired
  public UserService(UserAccessor userAccessor, UserMapper userMapper,
                     CityService cityService){
    this.userAccessor = userAccessor;
    this.userMapper = userMapper;
    this.cityService = cityService;
  }

  public List<UserDto> getAllUsers(){
    List<User> users = userAccessor.readAllUsers();
    List<UserDto> userDtos = userMapper.mapUsersToDtos(users);

    return userDtos;
  }

  public User getUserById(int id){
    User user = userAccessor.readUserById(id);

    return  user;
  }

  public User addUser(String username, int cityId,
                      String email, LocalDate joinDate) {
      userAccessor.readUsersByUsernameAndEmail(username, email);
      City city = cityService.getCityById(cityId);

      User user = new User(username, city, email, joinDate);
      userAccessor.addUser(user);

      return user;
  }

  public UserDto updateUser(int id, UserUpdateRequest userRequest){
    userAccessor.readUsersByUsernameAndEmail(userRequest.getUsername(), userRequest.getEmail());
    User user = getUserById(id);
    City city = cityService.getCityById(userRequest.getCity());

    User userNew = new User(id, userRequest.getUsername(),
                            city, userRequest.getEmail(), user.getJoinDate());

    userAccessor.updateUser(userNew);

    UserDto userDto = new UserDto(user.getId(), userNew.getUsername(),
                                  userNew.getCity(), userNew.getEmail(), user.getJoinDate());

    return userDto;
  }

  public UserDto deleteUser(int id){
    User user = getUserById(id);
    userAccessor.deleteUser(id);
    UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getCity(),
                                  user.getEmail(), user.getJoinDate());
    return userDto;
  }
}