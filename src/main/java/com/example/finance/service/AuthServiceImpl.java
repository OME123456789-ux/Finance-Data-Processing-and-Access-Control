package com.example.finance.service;

import com.example.finance.dto.AuthResponseDto;
import com.example.finance.dto.LoginRequestDto;
import com.example.finance.dto.SignupRequestDto;
import com.example.finance.dto.UserSummaryDto;
import com.example.finance.entity.User;
import com.example.finance.entity.UserRole;
import com.example.finance.entity.UserStatus;
import com.example.finance.exception.ConflictException;
import com.example.finance.exception.UnauthorizedException;
import com.example.finance.repository.UserRepository;
import com.example.finance.security.CustomUserDetails;
import com.example.finance.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  @Override
  public AuthResponseDto signup(SignupRequestDto request) {
    String email = normalizeEmail(request.getEmail());

    userRepository.findByEmail(email).ifPresent(u -> {
      throw new ConflictException("Email already in use");
    });

    User user = User.builder()
        .name(request.getName().trim())
        .email(email)
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .status(UserStatus.ACTIVE)
        .build();

    user = userRepository.save(user);

    CustomUserDetails details = CustomUserDetails.fromEntity(user);
    return buildAuthResponse(details);
  }

  @Override
  public AuthResponseDto login(LoginRequestDto request) {
    String email = normalizeEmail(request.getEmail());

    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(email, request.getPassword())
      );

      Object principal = authentication.getPrincipal();
      if (!(principal instanceof CustomUserDetails custom)) {
        throw new UnauthorizedException("Invalid credentials");
      }

      if (!custom.getStatus().equals(UserStatus.ACTIVE)) {
        throw new UnauthorizedException("User is not active");
      }

      return buildAuthResponse(custom);
    } catch (AuthenticationException ex) {
      throw new UnauthorizedException("Invalid credentials");
    }
  }

  private AuthResponseDto buildAuthResponse(CustomUserDetails userDetails) {
    String token = jwtService.generateToken(userDetails);

    UserSummaryDto user = new UserSummaryDto();
    user.setId(userDetails.getId());
    user.setName(userDetails.getName());
    user.setEmail(userDetails.getEmail());
    user.setRole(userDetails.getRole());
    user.setStatus(userDetails.getStatus());

    AuthResponseDto response = new AuthResponseDto();
    response.setToken(token);
    response.setUser(user);
    return response;
  }

  private static String normalizeEmail(String email) {
    if (email == null) return null;
    return email.trim().toLowerCase(Locale.ROOT);
  }
}

