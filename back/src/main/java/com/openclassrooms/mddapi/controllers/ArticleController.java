package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.payload.response.ArticleResponse;
import com.openclassrooms.mddapi.services.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    List<ArticleResponse> articleResponses= articleMapper.toDto(articleService.getAllArticles(authentication.getName()));
    return  ResponseEntity.ok(articleResponses);
}

  //POST /api/articles
  // GET /api/articles/{id_article}/comments
  //POST /api/articles/{id_articles}/comments

}
