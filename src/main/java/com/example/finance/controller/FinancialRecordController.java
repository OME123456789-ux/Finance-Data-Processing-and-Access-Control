package com.example.finance.controller;

import com.example.finance.dto.FinancialRecordCreateRequestDto;
import com.example.finance.dto.FinancialRecordResponseDto;
import com.example.finance.dto.FinancialRecordUpdateRequestDto;
import com.example.finance.entity.FinancialRecordType;
import com.example.finance.service.FinancialRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class FinancialRecordController {

  private final FinancialRecordService financialRecordService;

  @PostMapping
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<FinancialRecordResponseDto> create(@Valid @RequestBody FinancialRecordCreateRequestDto request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(financialRecordService.create(request));
  }

  @GetMapping
  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ANALYST','ROLE_VIEWER')")
  public Page<FinancialRecordResponseDto> list(
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) FinancialRecordType type,
      @PageableDefault(size = 20, sort = "date", direction = org.springframework.data.domain.Sort.Direction.DESC)
      Pageable pageable
  ) {
    return financialRecordService.list(date, category, type, pageable);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<FinancialRecordResponseDto> update(
      @PathVariable Long id,
      @Valid @RequestBody FinancialRecordUpdateRequestDto request
  ) {
    return ResponseEntity.ok(financialRecordService.update(id, request));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    financialRecordService.delete(id);
    return ResponseEntity.noContent().build();
  }
}

