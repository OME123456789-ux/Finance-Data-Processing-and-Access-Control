package com.example.finance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {
  private String token;
  private String tokenType = "Bearer";
  private UserSummaryDto user;
}

