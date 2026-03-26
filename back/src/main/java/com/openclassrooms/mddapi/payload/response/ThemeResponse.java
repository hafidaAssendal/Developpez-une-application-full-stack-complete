package com.openclassrooms.mddapi.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThemeResponse {

  private Long id;
  private String title;
  private String description;
  private boolean subscribed;
}
