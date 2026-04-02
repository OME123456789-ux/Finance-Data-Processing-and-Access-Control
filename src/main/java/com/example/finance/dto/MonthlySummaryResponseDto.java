package com.example.finance.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MonthlySummaryResponseDto {
  private List<MonthlyTotalsDto> monthly;
}

