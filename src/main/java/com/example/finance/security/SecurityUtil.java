package com.example.finance.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {

  private SecurityUtil() {
  }

  public static CustomUserDetails currentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || authentication.getPrincipal() == null) {
      throw new IllegalStateException("No authenticated user");
    }
    Object principal = authentication.getPrincipal();
    if (principal instanceof CustomUserDetails details) {
      return details;
    }
    throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
  }

  public static Long currentUserId() {
    return currentUser().getId();
  }
}

