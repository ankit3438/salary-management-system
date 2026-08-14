package com.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalarySummaryResponse {

    private Long totalEmployees;
    private BigDecimal averageSalary;
    private BigDecimal medianSalary;
    private BigDecimal minimumSalary;
    private BigDecimal maximumSalary;
}
