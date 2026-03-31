package com.openclassrooms.mddapi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class ArticleRequest {

  @NotBlank(message = "Le titre est obligatoire")
  @Size(max = 100)
  private String title;
  @NotBlank(message = "le contenue est obligatoire")
  private String content;
  @NotNull(message = "Veuillez ajouter un thème!")
  private Long  themeId;

}
