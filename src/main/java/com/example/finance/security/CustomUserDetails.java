package com.example.finance.security;

import com.example.finance.entity.User;
import com.example.finance.entity.UserRole;
import com.example.finance.entity.UserStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {

  private Long id;
  private String name;
  private String email;
  private String passwordHash;
  private UserRole role;
  private UserStatus status;

  public CustomUserDetails(Long id, String name, String email, String passwordHash, UserRole role, UserStatus status) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.passwordHash = passwordHash;
    this.role = role;
    this.status = status;
  }

  public static CustomUserDetails fromEntity(User user) {
    return new CustomUserDetails(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getPassword(),
        user.getRole(),
        user.getStatus()
    );
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // Spring's hasRole('ADMIN') expects ROLE_ prefix.
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getPassword() {
    return passwordHash;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return status == UserStatus.ACTIVE;
  }
}

