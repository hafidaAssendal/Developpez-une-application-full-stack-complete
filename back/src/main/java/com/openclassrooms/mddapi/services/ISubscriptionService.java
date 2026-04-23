package com.openclassrooms.mddapi.services;

public interface ISubscriptionService {
  void subscribe(String email, Long themeId);
  void unsubscribe(String email, Long themeId);
}
