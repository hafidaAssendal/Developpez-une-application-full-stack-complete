package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Theme;
import java.util.List;

public interface IThemeService {
  List<Theme> findAllThemes();
  boolean isSubscribed(String email, Long themeId);
}
