package com.diyanamancheva.user;

import com.diyanamancheva.city.City;
import com.diyanamancheva.city.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
  private UserAccessor userAccessor;
  private UserMapper userMapper;
  private CityService cityService;

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

  public UserDto updateUser(int id, UserRequest userRequest){
    userAccessor.readUsersByUsernameAndEmail(userRequest.getUsername(), userRequest.getEmail());
    User user = getUserById(id);
    City city = cityService.getCityById(userRequest.getCityId());

    User userNew = new User(id, userRequest.getUsername(),
                            city, userRequest.getEmail(), userRequest.getJoinDate());

    userAccessor.updateUser(userNew);

    UserDto userDto = new UserDto(user.getId(), user.getUsername(),
                                  user.getCity(), user.getEmail(), user.getJoinDate());

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
