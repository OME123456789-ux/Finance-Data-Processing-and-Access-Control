package com.example.finance.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ApiErrorResponse {
  private Instant timestamp;
  private int status;
  private String error;
  private String message;
  private String path;

  public ApiErrorResponse() {
    this.timestamp = Instant.now();
  }

  public ApiErrorResponse(int status, String error, String message, String path) {
    this();
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
  }
}

