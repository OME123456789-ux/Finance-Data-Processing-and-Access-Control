package com.example.finance.service;

import com.example.finance.dto.DashboardCategoryResponseDto;
import com.example.finance.dto.DashboardTotalsResponseDto;
import com.example.finance.dto.MonthlySummaryResponseDto;

public interface DashboardService {
  DashboardTotalsResponseDto totals();

  DashboardCategoryResponseDto categoryTotals();

  MonthlySummaryResponseDto monthlySummary();
}

