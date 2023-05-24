package com.diyanamancheva.controller;

import com.diyanamancheva.controller.request.user.UserRequest;
import com.diyanamancheva.controller.request.user.UserUpdateRequest;
import com.diyanamancheva.model.User;
import com.diyanamancheva.service.UserService;
import com.diyanamancheva.dto.user.UserDto;
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
import java.util.List;

@RestController
public class UserController {
  private final UserService userService;

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

  @PostMapping("/users")
  public ResponseEntity<Void> createUser(@RequestBody @Valid UserRequest userRequest){
    User user = userService.addUser(userRequest);

    URI location = UriComponentsBuilder.fromUriString("/users/{id}")
                                       .buildAndExpand(user.getId()).toUri();

    return ResponseEntity.created(location).build();
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<UserDto> updateUser (@RequestBody @Valid UserUpdateRequest userRequest,
                                                 @PathVariable int id,
                                                 @RequestParam(defaultValue="true") boolean returnOld){

    UserDto userDto = userService.updateUser(id, userRequest);
    if (returnOld){
      return ResponseEntity.ok(userDto);
    }else{
      return ResponseEntity.noContent().build();
    }
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<UserDto> deleteUser(@PathVariable int id,
                                                @RequestParam(defaultValue="true") boolean returnOld){
    UserDto userDto = userService.deleteUser(id);

    if(returnOld){
      return ResponseEntity.ok(userDto);
    } else {
      return ResponseEntity.noContent().build();
    }
  }
}
