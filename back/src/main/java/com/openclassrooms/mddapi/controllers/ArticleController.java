package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.payload.request.ArticleRequest;
import com.openclassrooms.mddapi.payload.response.ArticleResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.services.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/articles")
public class ArticleController {
private final  ArticleService articleService;
private final ArticleMapper articleMapper;

  public ArticleController(ArticleService articleService, ArticleMapper articleMapper) {
    this.articleService = articleService;
    this.articleMapper = articleMapper;
  }


  // GET /api/articles
@GetMapping
  public ResponseEntity<List<ArticleResponse>>getArticles(Authentication authentication){
  try {
    List<ArticleResponse> articleResponses= articleMapper.toDto(articleService.getAllArticles(authentication.getName()));
    return  ResponseEntity.ok(articleResponses);
  }catch (NotFoundException e){
    throw  new NotFoundException(e.getMessage());
  }

}
// GET /api/articles/{id_article}
  @GetMapping("/{id_article}")
  public ResponseEntity<ArticleResponse> detailArticle(@PathVariable Long id_article){
    Article article=articleService.getArticleById(id_article);
    return ResponseEntity.ok(articleMapper.toDto(article));

  }
  //POST /api/articles
  @PostMapping
  public ResponseEntity<ArticleResponse> createArticle(@Valid  @RequestBody ArticleRequest articleRequest,
                                                       Authentication authentication){
    try {
      Article article=articleService.createArticle(authentication.getName(),articleRequest);

      return ResponseEntity.ok(articleMapper.toDto(article));
    }catch (NotFoundException e){
      throw  new NotFoundException(e.getMessage());
    }catch (BadRequestException e){
      throw new BadRequestException( e.getMessage());
    }

  }

  // GET /api/articles/{id_article}/comments
  //POST /api/articles/{id_articles}/comments

}
