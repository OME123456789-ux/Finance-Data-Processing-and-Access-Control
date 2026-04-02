package com.example.finance.repository;

import com.example.finance.dto.CategoryTotalsDto;
import com.example.finance.dto.MonthlyTotalsDto;
import com.example.finance.entity.FinancialRecord;
import com.example.finance.entity.FinancialRecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.math.BigDecimal;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long>, JpaSpecificationExecutor<FinancialRecord> {

  @Query("""
      SELECT COALESCE(SUM(r.amount), 0)
      FROM FinancialRecord r
      WHERE r.type = :type
    """)
  BigDecimal sumAmountByType(@Param("type") FinancialRecordType type);

  @Query("""
      SELECT new com.example.finance.dto.CategoryTotalsDto(
        r.category,
        COALESCE(SUM(CASE WHEN r.type = :incomeType THEN r.amount ELSE 0 END), 0),
        COALESCE(SUM(CASE WHEN r.type = :expenseType THEN r.amount ELSE 0 END), 0)
      )
      FROM FinancialRecord r
      GROUP BY r.category
      ORDER BY r.category ASC
    """)
  List<CategoryTotalsDto> findCategoryTotals(
      @Param("incomeType") FinancialRecordType incomeType,
      @Param("expenseType") FinancialRecordType expenseType
  );

  @Query("""
      SELECT new com.example.finance.dto.MonthlyTotalsDto(
        YEAR(r.date),
        MONTH(r.date),
        COALESCE(SUM(CASE WHEN r.type = :incomeType THEN r.amount ELSE 0 END), 0),
        COALESCE(SUM(CASE WHEN r.type = :expenseType THEN r.amount ELSE 0 END), 0)
      )
      FROM FinancialRecord r
      GROUP BY YEAR(r.date), MONTH(r.date)
      ORDER BY YEAR(r.date) ASC, MONTH(r.date) ASC
    """)
  List<MonthlyTotalsDto> findMonthlyTotals(
      @Param("incomeType") FinancialRecordType incomeType,
      @Param("expenseType") FinancialRecordType expenseType
  );
}

