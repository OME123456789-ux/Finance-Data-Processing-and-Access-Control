package com.example.finance.dto;

import com.example.finance.entity.UserRole;
import com.example.finance.entity.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSummaryDto {
  private Long id;
  private String name;
  private String email;
  private UserRole role;
  private UserStatus status;
}

