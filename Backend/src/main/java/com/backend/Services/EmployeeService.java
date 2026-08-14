package com.backend.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.backend.dto.EmployeeRequest;
import com.backend.dto.EmployeeResponse;

public interface EmployeeService {

    Page<EmployeeResponse> getAllEmployees(Pageable pageable);

    Page<EmployeeResponse> searchEmployees(String search, String department, String country, String status, Pageable pageable);

    EmployeeResponse getEmployeeById(Long id);

    EmployeeResponse getEmployeeByCode(String employeeCode);

    EmployeeResponse createEmployee(EmployeeRequest request);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    void deleteEmployee(Long id);
}
