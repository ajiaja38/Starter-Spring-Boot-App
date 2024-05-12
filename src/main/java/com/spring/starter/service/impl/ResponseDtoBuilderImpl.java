package com.spring.starter.service.impl;

import org.springframework.stereotype.Service;

import com.spring.starter.model.dto.res.UserResponseDto;
import com.spring.starter.model.entity.AppUser;
import com.spring.starter.model.entity.User;
import com.spring.starter.service.ResponseDtoBuilder;

@Service
public class ResponseDtoBuilderImpl implements ResponseDtoBuilder {

  @Override
  public UserResponseDto userResponseTransformer(User user) {
    return UserResponseDto.builder()
    .id(user.getId())
    .name(user.getName())
    .email(user.getEmail())
    .phone(user.getPhone())
    .address(user.getAddress())
    .avatar(user.getAvatar())
    .role(user.getRoles().stream().map(role -> role.getRole()).toList())
    .birthDate(user.getBirthDate())
    .updatedAt(user.getUpdatedAt())
    .createdAt(user.getCreatedAt())
    .build();
  }

  @Override
  public AppUser appUserTransformer(User user) {
    return AppUser.builder()
    .id(user.getId())
    .email(user.getEmail())
    .password(user.getPassword())
    .roles(user.getRoles())
    .build();
  }
  
}
