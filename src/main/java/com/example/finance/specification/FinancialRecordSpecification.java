package com.example.finance.specification;

import com.example.finance.entity.FinancialRecord;
import com.example.finance.entity.FinancialRecordType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class FinancialRecordSpecification {

  private FinancialRecordSpecification() {
  }

  public static Specification<FinancialRecord> build(LocalDate date, String category, FinancialRecordType type) {
    Specification<FinancialRecord> spec = Specification.where(null);

    if (date != null) {
      spec = spec.and(hasDate(date));
    }
    if (category != null && !category.isBlank()) {
      spec = spec.and(hasCategory(category));
    }
    if (type != null) {
      spec = spec.and(hasType(type));
    }

    return spec;
  }

  private static Specification<FinancialRecord> hasDate(LocalDate date) {
    return (root, query, cb) -> cb.equal(root.get("date"), date);
  }

  private static Specification<FinancialRecord> hasCategory(String category) {
    String normalized = category.trim().toLowerCase();
    return (root, query, cb) -> cb.equal(cb.lower(root.get("category")), normalized);
  }

  private static Specification<FinancialRecord> hasType(FinancialRecordType type) {
    return (root, query, cb) -> cb.equal(root.get("type"), type);
  }
}

