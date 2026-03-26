package com.openclassrooms.mddapi.payload.response;

import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {
  private Long id;
  private String title;
  private String content;
  private LocalDateTime createdAt;
  private String  themeTitle;
  private String authorUsername;
}
