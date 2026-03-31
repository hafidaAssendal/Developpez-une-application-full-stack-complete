package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.UpdateProfileRequest;
import com.openclassrooms.mddapi.payload.response.UserResponse;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  public UserController(UserService userService,
                        UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  // GET /api/user/me
  @GetMapping("/me")
  public UserResponse getProfile(Authentication authentication) {
    User user = userService.getProfile(authentication.getName());
    return userMapper.toDto(user);
  }

  // PATCH /api/user/me
  @PatchMapping("/me")
  public UserResponse updateProfile(Authentication authentication,
                                    @Valid @RequestBody UpdateProfileRequest request) {
    User user = userService.updateProfile(authentication.getName(), request);
    return userMapper.toDto(user);

  }
}
