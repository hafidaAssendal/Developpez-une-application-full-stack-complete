package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ThemeService {
  private final ThemeRepository themeRepository;
  private final SubscriptionRepository subscriptionRepository;
  private final UserRepository userRepository;

  public ThemeService(ThemeRepository themeRepository,
                      SubscriptionRepository subscriptionRepository,
                      UserRepository userRepository) {
    this.themeRepository = themeRepository;
    this.subscriptionRepository = subscriptionRepository;
    this.userRepository = userRepository;
  }

  public List<Theme> findAllThemes() {
    return themeRepository.findAll();
  }

  public boolean isSubscribed(String mail, Long theme_id) {

    User user = userRepository.findByEmail(mail).orElseThrow(()-> new NotFoundException("Utilsateur Introuvable"));
    return subscriptionRepository.existsByUserIdAndTheme(user.getId(), theme_id);
  }
}
