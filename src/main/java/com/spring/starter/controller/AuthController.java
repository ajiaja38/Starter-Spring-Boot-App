package com.spring.starter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.starter.model.dto.req.LoginRequestDto;
import com.spring.starter.model.dto.req.RegisterRequestDto;
import com.spring.starter.model.dto.req.RequestRefreshTokenDto;
import com.spring.starter.model.dto.res.CommonResponseDto;
import com.spring.starter.model.dto.res.LoginResponseDto;
import com.spring.starter.model.dto.res.RefreshAccessTokenDto;
import com.spring.starter.model.dto.res.UserResponseDto;
import com.spring.starter.service.AuthService;
import com.spring.starter.utils.constant.ApiPathConstant;
import com.spring.starter.utils.constant.ERole;

import jakarta.validation.Valid;


@RestController
@RequestMapping(
  ApiPathConstant.API +
  ApiPathConstant.VERSION +
  ApiPathConstant.AUTH
)
@Validated
public class AuthController {
  
  @Autowired
  private AuthService authService;

  @PostMapping("register")
  public ResponseEntity<CommonResponseDto<UserResponseDto>>
  registerUserHandler(
    @Valid
    @RequestBody
    RegisterRequestDto registerRequestDto
  ) {
    return ResponseEntity
    .status(HttpStatus.CREATED)
    .body(
      new CommonResponseDto<>(
        HttpStatus.CREATED.value(),
        "SuccessFully Registered User",
        authService.registerUser(registerRequestDto, ERole.SUPER_ADMIN.name())
      )
    );
  }

  @PostMapping("register/user")
  public ResponseEntity<CommonResponseDto<UserResponseDto>>
  registerCommonUserHandler(
    @Valid
    @RequestBody
    RegisterRequestDto registerRequestDto
  ) {
    return ResponseEntity
    .status(HttpStatus.CREATED)
    .body(
      new CommonResponseDto<>(
        HttpStatus.CREATED.value(),
        "SuccessFully Registered User",
        authService.registerUser(registerRequestDto, ERole.USER.name())
      )
    );
  }

  @PostMapping("login")
  public ResponseEntity<CommonResponseDto<LoginResponseDto>>
  LoginHandler(
    @RequestBody
    LoginRequestDto loginRequestDto
  ) {
    return ResponseEntity
    .status(HttpStatus.OK)
    .body(
      new CommonResponseDto<>(
        HttpStatus.OK.value(),
        "SuccessFully Logged In",
        authService.login(loginRequestDto)
      )
    );
  }

  @PutMapping("refresh-token")
  public ResponseEntity<CommonResponseDto<RefreshAccessTokenDto>>
  refreshAccessTokenHandler(
    @RequestBody
    RequestRefreshTokenDto requestRefreshTokenDto
  ) {
    return ResponseEntity
    .status(HttpStatus.OK)
    .body(
      new CommonResponseDto<>(
        HttpStatus.OK.value(),
        "SuccessFully Refreshed Access Token",
        authService.refreshAccessToken(requestRefreshTokenDto)
      )
    );
  }

}
