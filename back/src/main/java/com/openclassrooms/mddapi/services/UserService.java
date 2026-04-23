
package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.request.UpdateUserRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService  implements IUserService{

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtils jwtUtils;

  public UserService(AuthenticationManager authenticationManager,
                     UserRepository userRepository,
                     PasswordEncoder passwordEncoder,
                     JwtUtils jwtUtils) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtils = jwtUtils;
  }

  // ===== LOGIN =====
  public JwtResponse login(LoginRequest request) {

    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getIdentifier(), request.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtUtils.generateJwtToken(authentication.getName());
    return new JwtResponse(jwt);
  }

  // ===== REGISTER =====
  public MessageResponse register(SignupRequest request) {

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new BadRequestException("Email déjà utilisé");
    }
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new BadRequestException("Nom d'utilisateur déjà utilisé");
    }

    User user = User.builder()
      .username(request.getUsername())
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .build();
    userRepository.save(user);
    return new MessageResponse("Inscription réussie !");
  }

  // ===== GET PROFILE =====
  public User getProfile(String email) {
    return userRepository.findByEmail(email)
      .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));
  }

  // ===== UPDATE PROFILE =====
  public User updateProfile(String email, UpdateUserRequest request) {

    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));

    if (!StringUtils.isEmpty(request.getUsername()) ) {
      if (!user.getUsername().equals(request.getUsername())
        && userRepository.existsByUsername(request.getUsername())) {
        throw new BadRequestException("Nom d'utilisateur déjà utilisé");
      }
      user.setUsername(request.getUsername());
    }

    if (!StringUtils.isEmpty(request.getEmail())){
      if (!user.getEmail().equals(request.getEmail())
        && userRepository.existsByEmail(request.getEmail())) {
        throw new BadRequestException("Email déjà utilisé");
      }
      user.setEmail(request.getEmail());
    }

    if (!StringUtils.isEmpty((request.getPassword()))){
      user.setPassword(passwordEncoder.encode(request.getPassword()));
    }

    return userRepository.save(user);
  }
}
