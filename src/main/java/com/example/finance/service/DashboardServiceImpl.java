package com.example.finance.service;

import com.example.finance.dto.CategoryTotalsDto;
import com.example.finance.dto.DashboardCategoryResponseDto;
import com.example.finance.dto.DashboardTotalsResponseDto;
import com.example.finance.dto.MonthlySummaryResponseDto;
import com.example.finance.dto.MonthlyTotalsDto;
import com.example.finance.entity.FinancialRecordType;
import com.example.finance.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

  private final FinancialRecordRepository financialRecordRepository;

  @Override
  public DashboardTotalsResponseDto totals() {
    BigDecimal income = financialRecordRepository
        .sumAmountByType(FinancialRecordType.INCOME);
    BigDecimal expenses = financialRecordRepository
        .sumAmountByType(FinancialRecordType.EXPENSE);

    DashboardTotalsResponseDto dto = new DashboardTotalsResponseDto();
    dto.setTotalIncome(income == null ? BigDecimal.ZERO : income);
    dto.setTotalExpenses(expenses == null ? BigDecimal.ZERO : expenses);
    dto.setNetBalance(dto.getTotalIncome().subtract(dto.getTotalExpenses()));
    return dto;
  }

  @Override
  public DashboardCategoryResponseDto categoryTotals() {
    List<CategoryTotalsDto> categories = financialRecordRepository
        .findCategoryTotals(FinancialRecordType.INCOME, FinancialRecordType.EXPENSE);

    DashboardCategoryResponseDto dto = new DashboardCategoryResponseDto();
    dto.setCategories(categories);
    return dto;
  }

  @Override
  public MonthlySummaryResponseDto monthlySummary() {
    List<MonthlyTotalsDto> monthly = financialRecordRepository
        .findMonthlyTotals(FinancialRecordType.INCOME, FinancialRecordType.EXPENSE);

    MonthlySummaryResponseDto dto = new MonthlySummaryResponseDto();
    dto.setMonthly(monthly);
    return dto;
  }
}

