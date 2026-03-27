package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.payload.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentResponse, Comment> {
  @Mapping(target = "articleId", source = "article.id")
  @Mapping(target = "authorUsername", source = "author.username")
  CommentResponse toDto(Comment comment);

}
