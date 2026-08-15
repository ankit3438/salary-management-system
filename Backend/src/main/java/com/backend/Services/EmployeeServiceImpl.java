package com.backend.Services;

import com.backend.common.exception.DuplicateEmployeeException;
import com.backend.common.exception.EmployeeNotFoundException;
import com.backend.dto.EmployeeFilterResponse;
import com.backend.dto.EmployeeRequest;
import com.backend.dto.EmployeeResponse;
import com.backend.entity.Employee;
import com.backend.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getAllEmployees(Pageable pageable) {
        log.info("Fetching all employees with pagination: {}", pageable);
        return employeeRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> searchEmployees(String search, String department, String country, String status,
            Pageable pageable) {
        log.info("Searching employees with filters - search: {}, department: {}, country: {}, status: {}", search,
                department, country, status);
        return employeeRepository.findByCriteria(search, department, country, status, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + " not found"));
        return mapToResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeByCode(String employeeCode) {
        log.info("Fetching employee with code: {}", employeeCode);
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with code " + employeeCode + " not found"));
        return mapToResponse(employee);
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        log.info("Creating employee with code: {}", request.getEmployeeCode());

        // Check if employee code already exists
        if (employeeRepository.findByEmployeeCode(request.getEmployeeCode()).isPresent()) {
            throw new DuplicateEmployeeException("Employee with code " + request.getEmployeeCode() + " already exists");
        }

        // Check if email already exists
        if (employeeRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmployeeException("Employee with email " + request.getEmail() + " already exists");
        }

        Employee employee = Employee.builder()
                .employeeCode(request.getEmployeeCode())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .department(request.getDepartment())
                .designation(request.getDesignation())
                .country(request.getCountry())
                .joiningDate(request.getJoiningDate())
                .status("ACTIVE")
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee created successfully with id: {}", savedEmployee.getId());
        return mapToResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        log.info("Updating employee with id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + " not found"));

        // Check if new email already exists (and is not the same employee)
        if (!employee.getEmail().equals(request.getEmail()) &&
                employeeRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmployeeException("Employee with email " + request.getEmail() + " already exists");
        }

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());
        employee.setCountry(request.getCountry());
        employee.setJoiningDate(request.getJoiningDate());

        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Employee updated successfully with id: {}", id);
        return mapToResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + " not found"));

        employeeRepository.delete(employee);
        log.info("Employee deleted successfully with id: {}", id);
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .designation(employee.getDesignation())
                .country(employee.getCountry())
                .joiningDate(employee.getJoiningDate())
                .status(employee.getStatus())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }

    @Override
    public EmployeeFilterResponse getEmployeeFilters() {
        // TODO Auto-generated method stub
        log.info("Fetching distinct departments and countries...");

    List<String> departments = employeeRepository.findDistinctDepartments();
    log.info("Departments found: {}", departments);

    List<String> countries = employeeRepository.findDistinctCountries();
    log.info("Countries found: {}", countries);

        return new EmployeeFilterResponse(
                departments,
                countries);
    }
}
