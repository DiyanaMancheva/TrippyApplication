package com.diyanamancheva.service;

import com.diyanamancheva.controller.request.user.UserRequest;
import com.diyanamancheva.controller.request.user.UserUpdateRequest;
import com.diyanamancheva.dto.mapper.UserMapper;
import com.diyanamancheva.dto.user.UserDto;
import com.diyanamancheva.exception.EntityNotFoundException;
import com.diyanamancheva.model.City;
import com.diyanamancheva.model.User;
import com.diyanamancheva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final CityService cityService;

  @Autowired
  public UserService(
    UserRepository userRepository, UserMapper userMapper,
    CityService cityService){
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.cityService = cityService;
  }

  public List<UserDto> getAllUsers(){
    Iterable<User> users = userRepository.findAll();
    List<UserDto> userDtos = userMapper.mapUsersToDtos(users);

    return userDtos;
  }

  public User getUserById(int id){
    User user = userRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d not found", id)));

    return  user;
  }

  public User addUser(UserRequest userRequest) {
      //userRepository.readUsersByUsernameAndEmail(username, email);

      User userNew = userMapper.mapUserRequestToUser(userRequest);
      userNew = userRepository.save(userNew);

      return userNew;
  }

  public UserDto updateUser(int id, UserUpdateRequest userRequest){
    //userRepository.readUsersByUsernameAndEmail(userRequest.getUsername(), userRequest.getEmail());
    User user = getUserById(id);
    City city = cityService.getCityById(userRequest.getCity());

    User userOld = new User(user.getId(), user.getUsername(),
                            city, user.getEmail(), user.getJoinDate());

    user.setUsername(userRequest.getUsername());
    user.setCity(city);
    user.setEmail(userRequest.getEmail());
    userRepository.save(user);

    UserDto userDto = userMapper.mapUserToDto(userOld);

    return userDto;
  }

  public UserDto deleteUser(int id){
    User user = getUserById(id);
    userRepository.deleteById(id);
    UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getCity(),
                                  user.getEmail(), user.getJoinDate());
    return userDto;
  }
}
