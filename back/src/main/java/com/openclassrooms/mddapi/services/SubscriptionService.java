package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionService {
  private final SubscriptionRepository subscriptionRepository;
  private final UserRepository userRepository;
  private final ThemeRepository themeRepository;

  public SubscriptionService(SubscriptionRepository subscriptionRepository,
                             UserRepository userRepository, ThemeRepository themeRepository) {
    this.subscriptionRepository = subscriptionRepository;
    this.userRepository = userRepository;
    this.themeRepository = themeRepository;
  }

  public void subscribe(String mail, Long id_theme) {
    User user = userRepository.findByEmail(mail).orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));
    Theme theme = themeRepository.findById(id_theme).orElseThrow(() -> new NotFoundException("Theme Introuvable"));
    if (subscriptionRepository.existsByUserIdAndTheme(user.getId(), id_theme)) {
      throw new BadRequestException("déja abonné à ce thème ");

    }
    Subscription subscription = new Subscription();
    subscription.setUser(user);
    subscription.setTheme(theme);
    subscriptionRepository.save(subscription);

  }
@Transactional
  public void unsubscribe(String mail, Long id_theme) {
    User user = userRepository.findByEmail(mail).orElseThrow(() -> new NotFoundException("utilisateur introuvable"));
    if (!subscriptionRepository.existsByUserIdAndTheme(user.getId(), id_theme)) {
      throw new BadRequestException("vous n'êtes pas abonné à ce thème");
    }
    subscriptionRepository.deleteByUserIdAndThemeId(user.getId(), id_theme);
  }
}
