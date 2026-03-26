package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;
 private final ThemeRepository themeRepository;
  public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, ThemeRepository themeRepository) {
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
    this.themeRepository = themeRepository;
  }

  public List<Article> getAllArticles(String mail) {
    User user = userRepository.findByEmail(mail).orElseThrow(()-> new NotFoundException("utilisateur introuvable"));
    return articleRepository.findArticlesByUserId(user.getId());
  }

}
