package com.fittrack.api.mapper;

import com.fittrack.api.dto.UserRequestDto;
import com.fittrack.api.dto.UserResponseDto;
import com.fittrack.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto toDto(User user) {
        if (user == null) return null;

        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isEmailVerified(user.isEmailVerified())
                .objectif(user.getObjectif())
                .build();
    }

    public User toEntity(UserRequestDto dto) {
        if (dto == null) return null;

        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .objectif(dto.getObjectif())
                .isEmailVerified(false)
                .build();
    }
}