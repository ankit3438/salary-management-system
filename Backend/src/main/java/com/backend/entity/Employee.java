package com.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee", indexes = {
    @Index(name = "idx_employee_code", columnList = "employee_code", unique = true),
    @Index(name = "idx_email", columnList = "email", unique = true),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_department", columnList = "department"),
    @Index(name = "idx_country", columnList = "country"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Employee code is required")
    @Column(nullable = false, unique = true, length = 50)
    private String employeeCode;

    @NotBlank(message = "First name is required")
    @Column(nullable = false, length = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(nullable = false, length = 100)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 100)
    private String department;

    @Column(length = 100)
    private String designation;

    @Column(length = 50)
    private String country;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(nullable = false, length = 50)
    @Builder.Default
    private String status = "ACTIVE";

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
