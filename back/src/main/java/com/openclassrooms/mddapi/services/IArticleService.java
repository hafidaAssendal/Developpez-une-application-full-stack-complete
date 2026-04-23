package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.payload.request.ArticleRequest;
import com.openclassrooms.mddapi.payload.request.CommentRequest;

import java.util.List;

public interface IArticleService {
  List<Article> getAllArticles(String email);
  Article getArticleById(String email, Long idArticle);
  Article createArticle(String email, ArticleRequest articleRequest);
  Comment createComment(Long idArticle, String email, CommentRequest commentRequest);
  List<Comment> getAllComments(Long articleId);
}
