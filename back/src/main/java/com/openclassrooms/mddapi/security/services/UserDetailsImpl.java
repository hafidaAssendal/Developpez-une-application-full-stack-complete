package com.openclassrooms.mddapi.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

@Builder
@AllArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

  private static final long serialVersionUID = 1L;
  private Long id;
  // username = email (utilisé par Spring Security en interne)
  private String username;
  // displayName = nom d'utilisateur MDD (affiché dans l'UI)
  private String displayName;
  private String email;
  @JsonIgnore
  private String password;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new HashSet<>();
  }
  @Override public boolean isAccountNonExpired()    { return true; }
  @Override public boolean isAccountNonLocked()     { return true; }
  @Override public boolean isCredentialsNonExpired(){ return true; }
  @Override public boolean isEnabled()              { return true; }
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return Objects.equals(id, ((UserDetailsImpl) o).id);
  }
}
