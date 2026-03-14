package com.openclassrooms.mddapi.payload.request;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class ProfileRequest {

  @NotBlank(message = "Le nom d'utilisateur est obligatoire")
  @Size(max = 50)
  private String username;

  @NotBlank(message = "L'email est obligatoire")
  @Size(max = 50)
  @Email(message = "Email invalide")
  private String email;

  // si l'utilisateur veut changer son mot de passe
  @Pattern(
    regexp = "^$|^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
    message = "8 caractères min, 1 majuscule, 1 minuscule, 1 chiffre, 1 caractère spécial"
  )
  private String password;
}
