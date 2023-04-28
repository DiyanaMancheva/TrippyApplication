package com.diyanamancheva.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private UserAccessor userAccessor;
  private UserMapper userMapper;

  @Autowired
  public UserService(UserAccessor userAccessor, UserMapper userMapper){
    this.userAccessor = userAccessor;
    this.userMapper = userMapper;
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
}
