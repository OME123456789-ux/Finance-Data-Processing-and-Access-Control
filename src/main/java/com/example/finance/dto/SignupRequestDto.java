package com.example.finance.dto;

import com.example.finance.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

  @NotBlank
  @Size(max = 100)
  private String name;

  @NotBlank
  @Email
  @Size(max = 150)
  private String email;

  @NotBlank
  @Size(min = 8, max = 72)
  private String password;

  @NotNull
  private UserRole role;
}

