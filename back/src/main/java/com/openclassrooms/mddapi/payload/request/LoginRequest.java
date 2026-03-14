package com.openclassrooms.mddapi.payload.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

  @NotBlank(message = "L'identifiant est obligatoire")
  private String identifier; // authentication soit avec email OU username

  @NotBlank(message = "Le mot de passe est obligatoire")
  private String password;
}
