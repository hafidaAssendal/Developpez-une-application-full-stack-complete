package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.payload.request.ArticleRequest;
import com.openclassrooms.mddapi.payload.request.CommentRequest;
import com.openclassrooms.mddapi.payload.response.ArticleResponse;
import com.openclassrooms.mddapi.payload.response.CommentResponse;
import com.openclassrooms.mddapi.services.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/articles")
public class ArticleController {
  private final ArticleService articleService;
  private final ArticleMapper articleMapper;
  private final CommentMapper commentMapper;

  public ArticleController(ArticleService articleService, ArticleMapper articleMapper, CommentMapper commentMapper) {
    this.articleService = articleService;
    this.articleMapper = articleMapper;
    this.commentMapper = commentMapper;
  }


  // GET /api/articles
  @GetMapping
  public List<ArticleResponse> getArticles(Authentication authentication) {
    return articleMapper.toDto(articleService.getAllArticles(authentication.getName()));
  }

  // GET /api/articles/{id_article}
  @GetMapping("/{id_article}")
  public ArticleResponse detailArticle(@PathVariable Long id_article) {
    Article article = articleService.getArticleById(id_article);
    return articleMapper.toDto(article);

  }

  //POST /api/articles
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED) //201
  public ArticleResponse createArticle(@Valid @RequestBody ArticleRequest articleRequest,
                                       Authentication authentication) {
    Article article = articleService.createArticle(authentication.getName(), articleRequest);
    return articleMapper.toDto(article);
  }

  // POST /api/articles/{id_article}/comments
  @PostMapping("/{id_article}/comments")
  @ResponseStatus(HttpStatus.CREATED)
  public CommentResponse addComment(@PathVariable Long id_article,
                                    Authentication authentication,
                                    @Valid @RequestBody CommentRequest commentRequest) {

    Comment comment = articleService.createComment(id_article, authentication.getName(), commentRequest);
    return commentMapper.toDto(comment);
 }

  //GET  /api/articles/{id_articles}/comments
  @GetMapping("/{id_article}/comments")
  public List<CommentResponse> getComments(@PathVariable Long id_article) {
    List<Comment> comments = articleService.getAllComments(id_article);
    return commentMapper.toDto(comments);
  }

}
