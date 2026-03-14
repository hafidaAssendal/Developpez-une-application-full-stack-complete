package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.ProfileRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;

  public AuthController(UserService authService) {
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
  public ResponseEntity<MessageResponse> register(
    @Valid @RequestBody SignupRequest request) {
    return ResponseEntity.ok(userService.register(request));
  }

  // GET /api/auth/me
  @GetMapping("/me")
  public ResponseEntity<ProfileRequest> getProfile(Authentication authentication) {
    return ResponseEntity.ok(userService.getProfile(authentication.getName()));
  }

  // PUT /api/auth/me
  @PutMapping("/me")
  public ResponseEntity<MessageResponse> updateProfile(
    Authentication authentication,
    @Valid @RequestBody ProfileRequest request) {
    return ResponseEntity.ok(
      userService.updateProfile(authentication.getName(), request));
  }
}
