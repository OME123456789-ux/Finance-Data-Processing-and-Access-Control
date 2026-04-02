package com.example.finance.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

  @NotBlank
  @Email
  @Size(max = 150)
  private String email;

  @NotBlank
  @Size(min = 8, max = 72)
  private String password;
}

