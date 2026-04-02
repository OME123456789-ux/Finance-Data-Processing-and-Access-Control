package com.example.finance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "financial_records")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FinancialRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FinancialRecordType type;

  @Column(nullable = false, length = 120)
  private String category;

  @Column(nullable = false)
  private LocalDate date;

  @Column(length = 1024)
  private String notes;

  // Optional owner reference (not used for filtering in shared-data mode).
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
}

