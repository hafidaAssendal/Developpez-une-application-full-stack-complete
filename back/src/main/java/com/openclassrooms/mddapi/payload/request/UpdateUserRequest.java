package com.openclassrooms.mddapi.payload.request;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class UpdateUserRequest {

  @Size(max = 50)
  private String username;

  @Email(message = "Email invalide")
  @Size(max = 50)
  private String email;

  @Pattern(
    regexp = "^$|^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$",
    message = "8 caractères min, 1 majuscule, 1 minuscule, 1 chiffre, 1 caractère spécial"
  )
  private String password;
}
