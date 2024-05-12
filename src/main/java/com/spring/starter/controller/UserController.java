package com.spring.starter.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.starter.model.dto.req.UpdateAvatarDto;
import com.spring.starter.model.dto.req.UpdateUserRequest;
import com.spring.starter.model.dto.res.CommonResponseDto;
import com.spring.starter.model.dto.res.ResponseMessageDto;
import com.spring.starter.model.dto.res.ResponsePageWrapper;
import com.spring.starter.model.dto.res.UserResponseDto;
import com.spring.starter.service.HeaderService;
import com.spring.starter.service.UserService;
import com.spring.starter.utils.constant.ApiPathConstant;

@RestController
@RequestMapping(
  ApiPathConstant.API +
  ApiPathConstant.VERSION +
  ApiPathConstant.USER
)
public class UserController {
  
  @Autowired
  private UserService userService;

  @Autowired
  private HeaderService headerService;

  @GetMapping
  @PreAuthorize("hasAuthority('SUPER_ADMIN')")
  public ResponseEntity<CommonResponseDto<List<UserResponseDto>>> getAllUsersHandler() {
    return ResponseEntity
    .status(HttpStatus.OK)
    .body(
      new CommonResponseDto<List<UserResponseDto>>(
        HttpStatus.OK.value(),
        "SuccessFully Retrieved Users",
        userService.getAllUsers()
      )
    );
  }

  @GetMapping("paginated")
  @PreAuthorize("hasAuthority('SUPER_ADMIN')")
  public ResponseEntity<ResponsePageWrapper<UserResponseDto>> getAllUserPage(
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "limit", defaultValue = "5") int pageSize,
    @RequestParam(name = "query", defaultValue = "") String query
  ) {
    return ResponseEntity
    .status(HttpStatus.OK)
    .body(
      new ResponsePageWrapper<>(
        HttpStatus.OK.value(),
        "SuccessFully Retrieved Users",
        userService.getAllUsers(query, PageRequest.of(page - 1, pageSize))
      )
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<CommonResponseDto<UserResponseDto>> getUserById(
    @PathVariable String id
  ) {
    return ResponseEntity
    .status(HttpStatus.OK)
    .body(
      new CommonResponseDto<UserResponseDto>(
        HttpStatus.OK.value(),
        "SuccessFully Retrieved User",
        userService.getUserById(id)
      )
    );
  }

  @GetMapping("profile")
  public ResponseEntity<CommonResponseDto<UserResponseDto>> getUserProfile(
    @RequestHeader(HttpHeaders.AUTHORIZATION) String token
  ) {
    return ResponseEntity
    .status(HttpStatus.OK)
    .body(
      new CommonResponseDto<UserResponseDto>(
        HttpStatus.OK.value(),
        "SuccessFully Retrieved User",
        this.userService.getUserById(headerService.userIdByToken(token.substring(7)))
      )
    );
  }

  @PutMapping("profile/update")
  @PreAuthorize("hasAuthority('USER')")
  public ResponseEntity<CommonResponseDto<UserResponseDto>> updateUserProfile(
    @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
    @RequestBody UpdateUserRequest updateUserRequest
  ) {
    return ResponseEntity
    .status(HttpStatus.OK)
    .body(
      new CommonResponseDto<>(
        HttpStatus.OK.value(),
        "SuccessFully Updated User",
        userService.UpdateUser(headerService.userIdByToken(token.substring(7)), updateUserRequest)
      )
    );
  }

  @PutMapping("{id}/update")
  @PreAuthorize("hasAuthority('SUPER_ADMIN')")
  public ResponseEntity<CommonResponseDto<UserResponseDto>> updateUserProfileByAdmin(
    @PathVariable String id,
    @RequestBody UpdateUserRequest updateUserRequest
  ) {
    return ResponseEntity
    .status(HttpStatus.OK)
    .body(
      new CommonResponseDto<>(
        HttpStatus.OK.value(),
        "SuccessFully Updated User",
        userService.UpdateUser(id, updateUserRequest)
      )
    );
  }

  @PutMapping("{id}/update/avatar")
  public ResponseEntity<CommonResponseDto<UserResponseDto>> updateUserAvatar(
    @PathVariable String id,
    @RequestBody UpdateAvatarDto updateAvatarDto
  ) {
    return ResponseEntity
    .status(HttpStatus.OK)
    .body(
      new CommonResponseDto<>(
        HttpStatus.OK.value(),
        "SuccessFully Updated Avatar User",
        userService.updateAvatar(id, updateAvatarDto)
      )
    );
  }

  @DeleteMapping("{id}")
  @PreAuthorize("hasAuthority('SUPER_ADMIN')")
  public ResponseEntity<ResponseMessageDto> deleteUserByIdHandler(
    @PathVariable String id
  ) {
    this.userService.deleteUser(id);
    return ResponseEntity
    .status(HttpStatus.OK)
    .body(
      new ResponseMessageDto(
        HttpStatus.OK.value(),
        "Successfully deleted user",
        new Date()
      )
    );
  }

}
