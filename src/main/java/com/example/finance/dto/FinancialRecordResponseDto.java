package com.example.finance.dto;

import com.example.finance.entity.FinancialRecordType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FinancialRecordResponseDto {
  private Long id;
  private BigDecimal amount;
  private FinancialRecordType type;
  private String category;
  private LocalDate date;
  private String notes;
}

