package com.example.finance.service;

import com.example.finance.dto.FinancialRecordCreateRequestDto;
import com.example.finance.dto.FinancialRecordResponseDto;
import com.example.finance.dto.FinancialRecordUpdateRequestDto;
import com.example.finance.entity.FinancialRecordType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface FinancialRecordService {
  FinancialRecordResponseDto create(FinancialRecordCreateRequestDto request);

  Page<FinancialRecordResponseDto> list(LocalDate date, String category, FinancialRecordType type, Pageable pageable);

  FinancialRecordResponseDto update(Long recordId, FinancialRecordUpdateRequestDto request);

  void delete(Long recordId);
}

