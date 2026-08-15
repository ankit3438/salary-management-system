package com.backend.dto;

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
    private Double totalPayroll;
    private Double averageSalary;
}
