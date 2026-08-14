package com.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryResponse {

    private Long id;
    private Long employeeId;
    private BigDecimal baseSalary;
    private BigDecimal bonus;
    private String currency;
    private LocalDate effectiveFrom;
    private LocalDateTime createdAt;
    private String createdBy;
}
