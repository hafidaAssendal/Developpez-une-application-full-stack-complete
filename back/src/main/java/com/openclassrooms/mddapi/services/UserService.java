
package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.ProfileRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

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

    // Cherche l'utilisateur par email OU username
    User user = userRepository
      .findByEmailOrUsername(request.getIdentifier(), request.getIdentifier())
      .orElseThrow(() -> new NotFoundException("Identifiants incorrects"));

    // Authentification Spring Security avec l'email
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(user.getEmail(), request.getPassword())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    return new JwtResponse( jwt );
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
  public ProfileRequest getProfile(String email) {

    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));

    ProfileRequest profile = new ProfileRequest();
    profile.setUsername(user.getUsername());
    profile.setEmail(user.getEmail());
    return profile;
  }

  // ===== UPDATE PROFILE =====
  public MessageResponse updateProfile(String email, ProfileRequest request) {

    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));

    // Vérifier que le nouvel email n'est pas déjà pris par quelqu'un d'autre
    if (!user.getEmail().equals(request.getEmail())
      && userRepository.existsByEmail(request.getEmail())) {
      throw new BadRequestException("Email déjà utilisé");
    }

    // Vérifier que le nouveau username n'est pas déjà pris par quelqu'un d'autre
    if (!user.getUsername().equals(request.getUsername())
      && userRepository.existsByUsername(request.getUsername())) {
      throw new BadRequestException("Nom d'utilisateur déjà utilisé");
    }

    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());

    // Changer le mot de passe seulement si fourni
    if (request.getPassword() != null && !request.getPassword().isEmpty()) {
      user.setPassword(passwordEncoder.encode(request.getPassword()));
    }

    userRepository.save(user);

    return new MessageResponse("Profil mis à jour avec succès !");
  }
}
