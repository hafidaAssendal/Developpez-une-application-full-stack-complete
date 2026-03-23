package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.response.UserResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserResponse, User> {
}
