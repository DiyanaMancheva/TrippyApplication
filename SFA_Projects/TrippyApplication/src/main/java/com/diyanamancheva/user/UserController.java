package com.diyanamancheva.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
  private UserService userService;

  @Autowired
  public UserController(UserService userService){
    this.userService = userService;
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserDto>> getAllUsers(){
    List<UserDto> userDtos = userService.getAllUsers();

    return ResponseEntity.ok(userDtos);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable int id){
    User user = userService.getUserById(id);
    UserDto userDto = new UserDto(user.getId(), user.getUsername(),
                                  user.getCity(), user.getEmail(),
                                  user.getJoinDate());

    return ResponseEntity.ok(userDto);
  }
}
