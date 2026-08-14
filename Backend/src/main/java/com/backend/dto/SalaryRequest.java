package com.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryRequest {

    @NotNull(message = "Base salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Base salary must be greater than 0")
    private BigDecimal baseSalary;

    @DecimalMin(value = "0.0", message = "Bonus cannot be negative")
    private BigDecimal bonus;

    private String currency;

    @NotNull(message = "Effective from date is required")
    private LocalDate effectiveFrom;
}
