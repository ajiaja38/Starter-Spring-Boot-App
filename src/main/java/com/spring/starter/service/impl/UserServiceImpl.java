package com.spring.starter.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.spring.starter.model.dto.req.UpdateAvatarDto;
import com.spring.starter.model.dto.req.UpdateUserRequest;
import com.spring.starter.model.dto.res.UserResponseDto;
import com.spring.starter.model.entity.AppUser;
import com.spring.starter.model.entity.User;
import com.spring.starter.repository.UserRepository;
import com.spring.starter.service.ResponseDtoBuilder;
import com.spring.starter.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ResponseDtoBuilder responseDtoBuilder;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = this.userRepository.findByEmail(username).orElseThrow(
      () -> new UsernameNotFoundException("Invalid Credentials")
    );

    return this.responseDtoBuilder.appUserTransformer(user);
  }

  @Override
  public AppUser loadUserByUserId(String id) {
    User user = this.userRepository.findById(id).orElseThrow(
      () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
    );

    return this.responseDtoBuilder.appUserTransformer(user);
  }

  @Override
  public List<UserResponseDto> getAllUsers() {
    List<User> users = this.userRepository.findAll();
    List<UserResponseDto> result = users.stream().map(this.responseDtoBuilder::userResponseTransformer).collect(Collectors.toList());
    return result;
  }

  @Override
  public Page<UserResponseDto> getAllUsers(String query, Pageable pageable) {
    Page<User> users;

    if (!query.isEmpty()) {
      users = this.userRepository.findByNameIgnoreCaseContainingOrEmailIgnoreCaseContainingOrAddressIgnoreCaseContaining(query, query, query, pageable);  
    } else {
      users = this.userRepository.findAll(pageable);
    }

    List<UserResponseDto> userResponseDtos = users.getContent().stream().map(this.responseDtoBuilder::userResponseTransformer).collect(Collectors.toList());
    return new PageImpl<>(userResponseDtos, pageable, users.getTotalElements());
  }

  @Override
  public UserResponseDto getUserById(String id) {
    User user = this.userRepository.findById(id).orElseThrow(
      () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
    );

    return this.responseDtoBuilder.userResponseTransformer(user);
  }

  @Override
  public UserResponseDto UpdateUser(String id, UpdateUserRequest updateUserRequest) {
    User user = this.userRepository.findById(id).orElseThrow(
      () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
    );

    user.setName(updateUserRequest.getName());
    user.setEmail(updateUserRequest.getEmail());
    user.setPhone(updateUserRequest.getPhone());
    user.setAddress(updateUserRequest.getAddress());
    this.userRepository.save(user);
    
    return this.responseDtoBuilder.userResponseTransformer(user);
  }

  @Override
  public UserResponseDto updateAvatar(String id, UpdateAvatarDto updateAvatarDto) {
    User user = this.userRepository.findById(id).orElseThrow(
      () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
    );

    user.setAvatar(updateAvatarDto.getAvatar());
    this.userRepository.save(user);
    return this.responseDtoBuilder.userResponseTransformer(user);
  }

  @Override
  public void deleteUser(String id) {
    User user = this.userRepository.findById(id).orElseThrow(
      () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
    );
    
    this.userRepository.delete(user);
  }
  
}
