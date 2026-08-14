package com.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "salary", indexes = {
    @Index(name = "idx_employee_id", columnList = "employee_id"),
    @Index(name = "idx_effective_from", columnList = "effective_from"),
    @Index(name = "idx_created_at", columnList = "created_at"),
    @Index(name = "idx_employee_effective_date", columnList = "employee_id, effective_from", unique = true)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Employee is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false, foreignKey = @ForeignKey(name = "fk_salary_employee"))
    private Employee employee;

    @NotNull(message = "Base salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Base salary must be greater than 0")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal baseSalary;

    @DecimalMin(value = "0.0", message = "Bonus cannot be negative")
    @Column(precision = 15, scale = 2)
    private BigDecimal bonus;

    @Column(nullable = false, length = 10)
    @Builder.Default
    private String currency = "USD";

    @NotNull(message = "Effective from date is required")
    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
