package com.spring.starter.model.dto.req;

import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
  
  @NotNull(message = "username shouldn't be null")
  private String name;

  @Email(message = "email should be valid")
  private String email;

  @NotNull
  @Pattern(regexp = "\\d{12}", message = "phone should be 12 digits")
  private String phone;
  private String address;
  
  private String password;

  private Date birthDate;

}
