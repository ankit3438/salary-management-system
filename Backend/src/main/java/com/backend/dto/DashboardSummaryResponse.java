package com.backend.dto;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardSummaryResponse {

    private Long totalEmployees;
    private Long activeEmployees;
    private Map<String, BigDecimal> totalPayrollByCurrency;
    private Map<String, Double> averageSalaryByCurrency;
}
