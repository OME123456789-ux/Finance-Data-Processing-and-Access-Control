package com.example.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTotalsDto {
  private String category;
  private BigDecimal incomeTotal;
  private BigDecimal expenseTotal;

  public BigDecimal getNetBalance() {
    if (incomeTotal == null) return BigDecimal.ZERO;
    if (expenseTotal == null) return incomeTotal;
    return incomeTotal.subtract(expenseTotal);
  }
}

