package com.openclassrooms.mddapi.mapper;


import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.payload.response.ThemeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ThemeMapper extends EntityMapper<ThemeResponse, Theme> {
  @Mapping(target = "subscribed", ignore = true)
  ThemeResponse toDto(Theme theme);

}
