package com.spring.starter.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.spring.starter.model.dto.req.UpdateAvatarDto;
import com.spring.starter.model.dto.req.UpdateUserRequest;
import com.spring.starter.model.dto.res.UserResponseDto;
import com.spring.starter.model.entity.AppUser;

public interface UserService extends UserDetailsService {
  AppUser loadUserByUserId(String id);
  List<UserResponseDto> getAllUsers();
  Page<UserResponseDto> getAllUsers(String query, Pageable pageable);
  UserResponseDto getUserById(String id);
  UserResponseDto UpdateUser(String id, UpdateUserRequest updateUserRequest);
  UserResponseDto updateAvatar(String id, UpdateAvatarDto updateAvatarDto);
  void deleteUser(String id);
}
