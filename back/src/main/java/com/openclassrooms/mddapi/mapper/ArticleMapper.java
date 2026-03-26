package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.payload.response.ArticleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntityMapper<ArticleResponse, Article> {
  @Mapping(target = "authorUsername", source = "author.username")
  @Mapping(target = "themeTitle", source = "theme.title")
    ArticleResponse toDto(Article article);

}
