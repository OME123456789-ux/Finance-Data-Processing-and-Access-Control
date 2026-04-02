package com.example.finance.dto;

import com.example.finance.entity.FinancialRecordType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FinancialRecordCreateRequestDto {

  @NotNull
  @DecimalMin(value = "0.01", inclusive = true)
  private BigDecimal amount;

  @NotNull
  private FinancialRecordType type;

  @NotBlank
  @Size(max = 120)
  private String category;

  @NotNull
  @PastOrPresent
  private LocalDate date;

  @Size(max = 1024)
  private String notes;
}

