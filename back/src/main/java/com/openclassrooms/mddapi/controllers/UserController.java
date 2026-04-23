package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.mapper.UpdateUserMapper;
import com.openclassrooms.mddapi.mapper.UserMapper;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.UpdateUserRequest;
import com.openclassrooms.mddapi.payload.response.UpdateUserResponse;
import com.openclassrooms.mddapi.payload.response.UserResponse;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.services.IUserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
 private final JwtUtils jwtUtils;
  private final IUserService userService;
  private final UserMapper userMapper;
  private final UpdateUserMapper updateUserMapper;

  public UserController(JwtUtils jwtUtils,
                        IUserService userService,
                        UserMapper userMapper,
                        UpdateUserMapper updateUserMapper) {
    this.jwtUtils = jwtUtils;
    this.userService = userService;
    this.userMapper = userMapper;
    this.updateUserMapper = updateUserMapper;
  }

  @GetMapping("/me")
  public UserResponse getProfile(Authentication authentication) {
    User user = userService.getProfile(authentication.getName());
    return userMapper.toDto(user);
  }

  @PatchMapping("/me")
  public UpdateUserResponse updateProfile(Authentication authentication,
                                          @Valid @RequestBody UpdateUserRequest request) {
    String oldEmail = authentication.getName();
    User user = userService.updateProfile(oldEmail, request);

    UpdateUserResponse response = updateUserMapper.toDto(user);

    if (request.getEmail() != null && !request.getEmail().equals(oldEmail)) {
      response.setToken(jwtUtils.generateJwtToken(user.getEmail()));
    }
    return  response;
  }
}
