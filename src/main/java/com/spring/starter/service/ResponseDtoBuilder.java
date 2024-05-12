package com.spring.starter.service;

import com.spring.starter.model.dto.res.UserResponseDto;
import com.spring.starter.model.entity.AppUser;
import com.spring.starter.model.entity.User;

public interface ResponseDtoBuilder {
  UserResponseDto userResponseTransformer(User user);
  AppUser appUserTransformer(User user);
}
