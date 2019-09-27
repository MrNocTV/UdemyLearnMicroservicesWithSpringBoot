package com.appsdeveloperblog.photoapp.api.users.ui.controllers;

import com.appsdeveloperblog.photoapp.api.users.service.UserService;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;
import com.appsdeveloperblog.photoapp.api.users.ui.controller.model.CreateUserRequestModel;
import com.appsdeveloperblog.photoapp.api.users.ui.controller.model.UserResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

  @Autowired
  private Environment env;

  @Autowired
  private UserService userService;

  @GetMapping("/status/check")
  public String status() {

    return "working on port " + env.getProperty("local.server.port");
  }

  @PostMapping(
          consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
          produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
  )
  public ResponseEntity createUser(@RequestBody CreateUserRequestModel userDetails) {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    UserDto userDto = modelMapper.map(userDetails, UserDto.class);

    UserDto createdUser = userService.createUser(userDto);

    UserResponseModel returnValue = modelMapper.map(createdUser, UserResponseModel.class);

    return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
  }

  @GetMapping(value="/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {

    UserDto userDto = userService.getUserByUserId(userId);
    UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);

    return ResponseEntity.status(HttpStatus.OK).body(returnValue);
  }
}
