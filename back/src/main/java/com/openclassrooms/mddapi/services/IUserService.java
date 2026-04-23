package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.request.UpdateUserRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;

public interface IUserService {
  JwtResponse login(LoginRequest request);
  MessageResponse register(SignupRequest request);
  User getProfile(String email);
  User updateProfile(String email, UpdateUserRequest request);
}
