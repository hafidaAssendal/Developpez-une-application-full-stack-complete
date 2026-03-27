package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.ArticleRequest;
import com.openclassrooms.mddapi.payload.response.ArticleResponse;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.Builder;
import org.mapstruct.control.MappingControl;
import org.springframework.stereotype.Service;

import java.util.List;

@Builder
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
    User user = userRepository.findByEmail(mail).orElseThrow(() -> new NotFoundException("utilisateur introuvable"));
    return articleRepository.findArticlesByUserId(user.getId());
  }

  public Article getArticleById(Long idArticle) {
    return articleRepository.findById(idArticle).orElseThrow(() -> new NotFoundException("Article  introuvable!"));

  }

  public Article createArticle(String mail, ArticleRequest articleRequest) {
    User user = userRepository.findByEmail(mail).orElseThrow(() -> new NotFoundException("Utilistaeur introuvable"));
    Theme theme = themeRepository.findById(articleRequest.getThemeId()).orElseThrow(() -> new NotFoundException("Theme introuvable"));
    Article article = Article.builder()
      .title(articleRequest.getTitle())
      .content(articleRequest.getContent())
      .author(user)
      .theme(theme)
      .build();
    return articleRepository.save(article);
  }
}
