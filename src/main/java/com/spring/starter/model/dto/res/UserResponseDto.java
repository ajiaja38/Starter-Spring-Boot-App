package com.spring.starter.model.dto.res;

import java.util.Date;
import java.util.List;

import com.spring.starter.utils.constant.ERole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
  
  private String id;
  private String name;
  private String email;
  private String phone;
  private String address;
  private String avatar;
  private List<ERole> role;
  private Date birthDate;
  private Date createdAt;
  private Date updatedAt;

}
