package com.spring.starter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.spring.starter.model.dto.req.LoginRequestDto;
import com.spring.starter.model.dto.req.RegisterRequestDto;
import com.spring.starter.model.dto.req.RequestRefreshTokenDto;
import com.spring.starter.model.dto.res.LoginResponseDto;
import com.spring.starter.model.dto.res.RefreshAccessTokenDto;
import com.spring.starter.model.dto.res.UserResponseDto;
import com.spring.starter.model.entity.AppUser;
import com.spring.starter.model.entity.Role;
import com.spring.starter.model.entity.User;
import com.spring.starter.repository.UserRepository;
import com.spring.starter.security.JwtUtils;
import com.spring.starter.service.AuthService;
import com.spring.starter.service.ResponseDtoBuilder;
import com.spring.starter.service.RoleService;
import com.spring.starter.utils.constant.ERole;

import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
  
  @Autowired
  private RoleService roleService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private ResponseDtoBuilder responseDtoBuilder;


  @Override
  @Transactional
  public UserResponseDto registerUser(RegisterRequestDto registerRequestDto, String roleRegister) {
    try {
      List<ERole> roles = new ArrayList<>();
      
      if (roleRegister.equals(ERole.SUPER_ADMIN.name())) {
        roles.add(ERole.SUPER_ADMIN);
      } else if (roleRegister.equals(ERole.ADMIN.name())) {
        roles.add(ERole.ADMIN);
      } else {
        roles.add(ERole.USER);
      }

      List<Role> userRoles = roles.stream().map(this.roleService::getOrSave).toList();

      User user = User.builder()
      .name(registerRequestDto.getName())
      .email(registerRequestDto.getEmail())
      .phone(registerRequestDto.getPhone())
      .address(registerRequestDto.getAddress())
      .password(this.passwordEncoder.encode(registerRequestDto.getPassword()))
      .birthDate(registerRequestDto.getBirthDate())
      .createdAt(new Date())
      .updatedAt(new Date())
      .roles(userRoles)
      .build();

      this.userRepository.save(user);

      return this.responseDtoBuilder.userResponseTransformer(user);

    } catch (DataIntegrityViolationException e) {
      throw e;
    }
  }

  @Override
  public LoginResponseDto login(LoginRequestDto loginRequestDto) {
    try {
      Authentication authentication = this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          loginRequestDto.getEmail(),
          loginRequestDto.getPassword())
      );

      SecurityContextHolder.getContext().setAuthentication(authentication);
      AppUser appUser = (AppUser) authentication.getPrincipal();

      String accessToken = this.jwtUtils.generatedAccessToken(appUser);
      String refreshToken = this.jwtUtils.generatedRefreshToken(appUser);

      return LoginResponseDto.builder()
      .accessToken(accessToken)
      .refreshToken(refreshToken)
      .build();

    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials!");
    }
  }

  @Override
  public RefreshAccessTokenDto refreshAccessToken(RequestRefreshTokenDto requestRefreshTokenDto) {
    try {
      return RefreshAccessTokenDto.builder()
      .accessToken(this.jwtUtils.refreshAccessToken(requestRefreshTokenDto.getRefreshToken()))
      .build();
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid RefreshToken");
    }
  }

}
