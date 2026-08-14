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
public class DepartmentSalaryResponse {

    private String department;
    private Long employeeCount;
    private BigDecimal averageSalary;
    private BigDecimal medianSalary;
    private BigDecimal minimumSalary;
    private BigDecimal maximumSalary;
}
