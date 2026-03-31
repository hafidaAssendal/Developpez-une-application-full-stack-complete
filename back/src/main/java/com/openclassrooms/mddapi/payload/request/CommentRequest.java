package com.openclassrooms.mddapi.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentRequest {

  @NotBlank(message = "commentaire vide!")
  String content;
}
