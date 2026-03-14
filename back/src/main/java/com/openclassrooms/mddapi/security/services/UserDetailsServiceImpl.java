package com.openclassrooms.mddapi.security.services;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
    // MDD : connexion par email OU username selon les specs
    User user = userRepository
      .findByEmailOrUsername(identifier, identifier)
      .orElseThrow(() -> new UsernameNotFoundException(
        "Utilisateur introuvable : " + identifier));

    return UserDetailsImpl.builder()
      .id(user.getId())
      .username(user.getEmail())      // Spring Security utilise email
      .displayName(user.getUsername()) // username affiché MDD
      .email(user.getEmail())
      .password(user.getPassword())
      .build();
  }
}
