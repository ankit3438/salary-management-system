package com.backend.Services;

import com.backend.common.exception.EmployeeNotFoundException;
import com.backend.common.exception.SalaryNotFoundException;
import com.backend.dto.SalaryRequest;
import com.backend.dto.SalaryResponse;
import com.backend.entity.Employee;
import com.backend.entity.Salary;
import com.backend.repository.EmployeeRepository;
import com.backend.repository.SalaryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public SalaryResponse addSalary(Long employeeId, SalaryRequest request) {
        log.info("Adding salary for employee id: {}", employeeId);

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + employeeId + " not found"));

        // Check if salary with same effective date already exists
        if (salaryRepository.existsByEmployeeIdAndEffectiveFrom(employeeId, request.getEffectiveFrom())) {
            throw new IllegalArgumentException("Salary record already exists for effective date: " + request.getEffectiveFrom());
        }

        Salary salary = Salary.builder()
                .employee(employee)
                .baseSalary(request.getBaseSalary())
                .bonus(request.getBonus() != null ? request.getBonus() : BigDecimal.ZERO)
                .currency(request.getCurrency() != null ? request.getCurrency() : "USD")
                .effectiveFrom(request.getEffectiveFrom())
                .createdBy("SYSTEM")
                .build();

        Salary savedSalary = salaryRepository.save(salary);
        log.info("Salary added successfully with id: {} for employee: {}", savedSalary.getId(), employeeId);
        return mapToResponse(savedSalary);
    }

    @Override
    @Transactional(readOnly = true)
    public SalaryResponse getCurrentSalary(Long employeeId) {
        log.info("Fetching current salary for employee id: {}", employeeId);

        // Verify employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException("Employee with id " + employeeId + " not found");
        }

        Salary salary = salaryRepository.findCurrentSalaryByEmployeeId(employeeId)
                .orElseThrow(() -> new SalaryNotFoundException("No salary record found for employee id: " + employeeId));

        return mapToResponse(salary);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalaryResponse> getSalaryHistory(Long employeeId) {
        log.info("Fetching salary history for employee id: {}", employeeId);

        // Verify employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException("Employee with id " + employeeId + " not found");
        }

        return salaryRepository.findSalaryHistoryByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalaryResponse> getSalaryHistoryPaginated(Long employeeId, Pageable pageable) {
        log.info("Fetching paginated salary history for employee id: {}", employeeId);

        // Verify employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException("Employee with id " + employeeId + " not found");
        }

        return salaryRepository.findSalaryHistoryByEmployeeIdPaginated(employeeId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public SalaryResponse getSalaryById(Long salaryId) {
        log.info("Fetching salary with id: {}", salaryId);
        Salary salary = salaryRepository.findById(salaryId)
                .orElseThrow(() -> new SalaryNotFoundException("Salary with id " + salaryId + " not found"));
        return mapToResponse(salary);
    }

    private SalaryResponse mapToResponse(Salary salary) {
        return SalaryResponse.builder()
                .id(salary.getId())
                .employeeId(salary.getEmployee().getId())
                .baseSalary(salary.getBaseSalary())
                .bonus(salary.getBonus())
                .currency(salary.getCurrency())
                .effectiveFrom(salary.getEffectiveFrom())
                .createdAt(salary.getCreatedAt())
                .createdBy(salary.getCreatedBy())
                .build();
    }
}
