package com.spring.starter.service;

import com.spring.starter.model.dto.req.LoginRequestDto;
import com.spring.starter.model.dto.req.RegisterRequestDto;
import com.spring.starter.model.dto.req.RequestRefreshTokenDto;
import com.spring.starter.model.dto.res.LoginResponseDto;
import com.spring.starter.model.dto.res.RefreshAccessTokenDto;
import com.spring.starter.model.dto.res.UserResponseDto;

public interface AuthService {
  UserResponseDto registerUser(RegisterRequestDto registerRequestDto, String roleRegister);
  LoginResponseDto login(LoginRequestDto loginRequestDto);
  RefreshAccessTokenDto refreshAccessToken(RequestRefreshTokenDto requestRefreshTokenDto);
}
