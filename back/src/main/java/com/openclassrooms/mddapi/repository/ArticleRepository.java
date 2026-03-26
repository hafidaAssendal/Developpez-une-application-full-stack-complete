package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
  @Query("SELECT a FROM Article a " +
          "WHERE a.theme.id IN " +
          "(SELECT s.theme.id FROM Subscription s WHERE s.user.id = :userId) " +
          "ORDER BY a.createdAt DESC")
  List<Article> findArticlesByUserId(@Param("userId") Long userId);
}
