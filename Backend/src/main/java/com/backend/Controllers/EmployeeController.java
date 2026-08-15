package com.backend.Controllers;

import com.backend.Services.EmployeeService;
import com.backend.dto.EmployeeFilterResponse;
import com.backend.dto.EmployeeRequest;
import com.backend.dto.EmployeeResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor // ---------------> for constructor injection using lombok
@Slf4j
//we have implemented crud operation for employee table or we can say for entity....
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("GET /api/employees - Fetching all employees, page={}, size={}", page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<EmployeeResponse> employees = employeeService.getAllEmployees(pageable);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EmployeeResponse>> searchEmployees(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("GET /api/employees/search - Searching employees with criteria, page={}, size={}", page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<EmployeeResponse> employees = employeeService.searchEmployees(
                search, department, country, status, pageable);

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/filters")
public ResponseEntity<EmployeeFilterResponse> getEmployeeFilters() {

    log.info("GET /api/employees/filters");

    return ResponseEntity.ok(
            employeeService.getEmployeeFilters()
    );
}

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        log.info("GET /api/employees/{} - Fetching employee by id", id);
        EmployeeResponse employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/code/{employeeCode}")
    public ResponseEntity<EmployeeResponse> getEmployeeByCode(@PathVariable String employeeCode) {
        log.info("GET /api/employees/code/{} - Fetching employee by code", employeeCode);
        EmployeeResponse employee = employeeService.getEmployeeByCode(employeeCode);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        log.info("POST /api/employees - Creating new employee");
        EmployeeResponse employee = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {
        log.info("PUT /api/employees/{} - Updating employee", id);
        EmployeeResponse employee = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("DELETE /api/employees/{} - Deleting employee", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
