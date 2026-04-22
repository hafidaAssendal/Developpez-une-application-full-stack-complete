package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.response.UpdateUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UpdateUserMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "username", source = "username")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "token", ignore = true)
  UpdateUserResponse toDto(User user);
}
