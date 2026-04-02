package com.example.finance.service;

import com.example.finance.dto.FinancialRecordCreateRequestDto;
import com.example.finance.dto.FinancialRecordResponseDto;
import com.example.finance.dto.FinancialRecordUpdateRequestDto;
import com.example.finance.entity.FinancialRecord;
import com.example.finance.entity.FinancialRecordType;
import com.example.finance.exception.NotFoundException;
import com.example.finance.repository.FinancialRecordRepository;
import com.example.finance.specification.FinancialRecordSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FinancialRecordServiceImpl implements FinancialRecordService {

  private final FinancialRecordRepository financialRecordRepository;

  @Override
  public FinancialRecordResponseDto create(FinancialRecordCreateRequestDto request) {
    FinancialRecord record = FinancialRecord.builder()
        .amount(request.getAmount())
        .type(request.getType())
        .category(request.getCategory().trim())
        .date(request.getDate())
        .notes(request.getNotes() == null || request.getNotes().isBlank() ? null : request.getNotes().trim())
        .build();

    FinancialRecord saved = financialRecordRepository.save(record);
    return toDto(saved);
  }

  @Override
  public Page<FinancialRecordResponseDto> list(LocalDate date, String category, FinancialRecordType type, Pageable pageable) {
    return financialRecordRepository.findAll(
        FinancialRecordSpecification.build(date, category, type),
        pageable
    ).map(this::toDto);
  }

  @Override
  public FinancialRecordResponseDto update(Long recordId, FinancialRecordUpdateRequestDto request) {
    FinancialRecord record = financialRecordRepository.findById(recordId)
        .orElseThrow(() -> new NotFoundException("Record not found"));

    record.setAmount(request.getAmount());
    record.setType(request.getType());
    record.setCategory(request.getCategory().trim());
    record.setDate(request.getDate());
    record.setNotes(request.getNotes() == null || request.getNotes().isBlank() ? null : request.getNotes().trim());

    FinancialRecord saved = financialRecordRepository.save(record);
    return toDto(saved);
  }

  @Override
  public void delete(Long recordId) {
    FinancialRecord record = financialRecordRepository.findById(recordId)
        .orElseThrow(() -> new NotFoundException("Record not found"));

    financialRecordRepository.delete(record);
  }

  private FinancialRecordResponseDto toDto(FinancialRecord record) {
    FinancialRecordResponseDto dto = new FinancialRecordResponseDto();
    dto.setId(record.getId());
    dto.setAmount(record.getAmount());
    dto.setType(record.getType());
    dto.setCategory(record.getCategory());
    dto.setDate(record.getDate());
    dto.setNotes(record.getNotes());
    return dto;
  }
}

