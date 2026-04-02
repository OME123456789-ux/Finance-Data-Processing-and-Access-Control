package com.example.finance.service;

import com.example.finance.dto.AuthResponseDto;
import com.example.finance.dto.LoginRequestDto;
import com.example.finance.dto.SignupRequestDto;

public interface AuthService {
  AuthResponseDto signup(SignupRequestDto request);

  AuthResponseDto login(LoginRequestDto request);
}

