package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.mapper.ThemeMapper;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.payload.response.ThemeResponse;
import com.openclassrooms.mddapi.services.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/themes")
public class ThemeController {
  private final ThemeService themeService;
  private final ThemeMapper themeMapper;

  public ThemeController(ThemeService themeService, ThemeMapper themeMapper) {
    this.themeService = themeService;
    this.themeMapper = themeMapper;
  }

  // GET /api/themes
  @GetMapping
  public List<ThemeResponse> getThemes(Authentication authentication) {
    List<Theme> themes = themeService.findAllThemes();
    List<ThemeResponse> ThemesResponses = themes.stream()
      .map(theme -> {
        ThemeResponse dto = themeMapper.toDto(theme);
        dto.setSubscribed(themeService.isSubscribed(authentication.getName(), theme.getId()));
        return dto;
      })
      .collect(Collectors.toList());
    return ThemesResponses;
  }


}
