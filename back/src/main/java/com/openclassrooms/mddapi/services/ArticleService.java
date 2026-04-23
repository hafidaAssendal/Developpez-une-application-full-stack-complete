package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.ArticleRequest;
import com.openclassrooms.mddapi.payload.request.CommentRequest;
import com.openclassrooms.mddapi.repository.*;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;

@Builder
@Service
public class ArticleService implements  IArticleService{
  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;
  private final ThemeRepository themeRepository;
  private final CommentRepository commentRepository;
  private final SubscriptionRepository subscriptionRepository;

  public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, ThemeRepository themeRepository, CommentRepository commentRepository, SubscriptionRepository subscriptionRepository) {
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
    this.themeRepository = themeRepository;
    this.commentRepository = commentRepository;
    this.subscriptionRepository = subscriptionRepository;
  }

  public List<Article> getAllArticles(String mail) {
    User user = userRepository.findByEmail(mail).orElseThrow(() -> new NotFoundException("utilisateur introuvable"));
    return articleRepository.findArticlesByUserId(user.getId());
  }

  public Article getArticleById(String mail, Long idArticle) {
    User user = userRepository.findByEmail(mail).orElseThrow(()-> new NotFoundException("utilisateur introuvable!"));
    Article article= articleRepository.findById(idArticle).orElseThrow(() -> new NotFoundException("Article  introuvable!"));
    if(!subscriptionRepository.existsByUserIdAndTheme(user.getId(), article.getTheme().getId())) {
      throw new NotFoundException("vous n'etes pas abonnée au thème de cette article !");
    }
    return article;
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

  public Comment createComment(Long idArticle, String mail, CommentRequest commentRequest) {
    User user = userRepository.findByEmail(mail).orElseThrow(() -> new NotFoundException("utilisateur introuvable"));
    Article article = articleRepository.findById(idArticle).orElseThrow(() -> new NotFoundException("Article introuvable"));

    Comment comment = Comment.builder().content(commentRequest.getContent())
      .article(article)
      .author(user)
      .build();
    return commentRepository.save(comment);
  }

  public List<Comment> getAllComments(Long articleId) {
    if (articleRepository.existsById(articleId)) {
      return commentRepository.findByArticleIdOrderByCreatedAtAsc(articleId);
    }
    throw new NotFoundException("Article introuvable");

  }
}
