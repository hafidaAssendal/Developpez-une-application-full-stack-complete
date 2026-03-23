package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;

  public AuthController(UserService authService, UserMapper userMapper) {
    this.userService = authService;

  }

  // POST /api/auth/login
  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(
    @Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(userService.login(request));
  }

  // POST /api/auth/register
  @PostMapping("/register")
  public ResponseEntity<MessageResponse> register(@Valid @RequestBody SignupRequest request) {
    return ResponseEntity.ok(userService.register(request));
  }

}
