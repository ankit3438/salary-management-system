package com.backend.Controllers;

import com.backend.Services.SalaryService;
import com.backend.dto.SalaryRequest;
import com.backend.dto.SalaryResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees/{employeeId}/salary")
@RequiredArgsConstructor
@Slf4j
public class SalaryController {

    private final SalaryService salaryService;

    @PostMapping
    public ResponseEntity<SalaryResponse> addSalary(
            @PathVariable Long employeeId,
            @Valid @RequestBody SalaryRequest request) {
        log.info("POST /api/employees/{}/salary - Adding salary for employee", employeeId);
        SalaryResponse salary = salaryService.addSalary(employeeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(salary);
    }

    @GetMapping
    public ResponseEntity<SalaryResponse> getCurrentSalary(@PathVariable Long employeeId) {
        log.info("GET /api/employees/{}/salary - Fetching current salary for employee", employeeId);
        SalaryResponse salary = salaryService.getCurrentSalary(employeeId);
        return ResponseEntity.ok(salary);
    }

    @GetMapping("/history")
    public ResponseEntity<List<SalaryResponse>> getSalaryHistory(@PathVariable Long employeeId) {
        log.info("GET /api/employees/{}/salary/history - Fetching salary history for employee", employeeId);
        List<SalaryResponse> salaryHistory = salaryService.getSalaryHistory(employeeId);
        return ResponseEntity.ok(salaryHistory);
    }

    @GetMapping("/history/paginated")
    public ResponseEntity<Page<SalaryResponse>> getSalaryHistoryPaginated(
            @PathVariable Long employeeId,
            @PageableDefault(size = 10, sort = "effectiveFrom", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("GET /api/employees/{}/salary/history/paginated - Fetching paginated salary history for employee", employeeId);
        Page<SalaryResponse> salaryHistory = salaryService.getSalaryHistoryPaginated(employeeId, pageable);
        return ResponseEntity.ok(salaryHistory);
    }

    @GetMapping("/{salaryId}")
    public ResponseEntity<SalaryResponse> getSalaryById(
            @PathVariable Long employeeId,
            @PathVariable Long salaryId) {
        log.info("GET /api/employees/{}/salary/{} - Fetching salary by id", employeeId, salaryId);
        SalaryResponse salary = salaryService.getSalaryById(salaryId);
        return ResponseEntity.ok(salary);
    }
}
