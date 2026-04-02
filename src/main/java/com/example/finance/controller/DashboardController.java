package com.example.finance.controller;

import com.example.finance.dto.DashboardCategoryResponseDto;
import com.example.finance.dto.DashboardTotalsResponseDto;
import com.example.finance.dto.MonthlySummaryResponseDto;
import com.example.finance.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

  private final DashboardService dashboardService;

  @GetMapping("/totals")
  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ANALYST')")
  public ResponseEntity<DashboardTotalsResponseDto> totals() {
    return ResponseEntity.ok(dashboardService.totals());
  }

  @GetMapping("/categories")
  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ANALYST')")
  public ResponseEntity<DashboardCategoryResponseDto> categoryTotals() {
    return ResponseEntity.ok(dashboardService.categoryTotals());
  }

  @GetMapping("/monthly")
  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ANALYST')")
  public ResponseEntity<MonthlySummaryResponseDto> monthlySummary() {
    return ResponseEntity.ok(dashboardService.monthlySummary());
  }
}

