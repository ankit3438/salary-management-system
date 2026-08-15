package com.backend.Controllers;

import com.backend.Services.AnalyticsService;
import com.backend.dto.CountrySalaryResponse;
import com.backend.dto.DashboardSummaryResponse;
import com.backend.dto.DepartmentSalaryResponse;
import com.backend.dto.SalarySummaryResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryResponse> getDashboardSummary() {
        log.info("GET /api/analytics/summary - Fetching dashboard summary");
        DashboardSummaryResponse summary = analyticsService.getDashboardSummary();
        return ResponseEntity.ok(summary);
    }
    

    // @GetMapping("/summary")
    // public ResponseEntity<SalarySummaryResponse> getSalarySummary() {
    //     log.info("GET /api/analytics/summary - Fetching salary summary");
    //     SalarySummaryResponse summary = analyticsService.getSalarySummary();
    //     return ResponseEntity.ok(summary);
    // }

    @GetMapping("/by-department")
    public ResponseEntity<List<DepartmentSalaryResponse>> getSalaryByDepartment() {
        log.info("GET /api/analytics/by-department - Fetching salary by department");
        List<DepartmentSalaryResponse> departmentSalaries = analyticsService.getSalaryByDepartment();
        return ResponseEntity.ok(departmentSalaries);
    }

    @GetMapping("/by-country")
    public ResponseEntity<List<CountrySalaryResponse>> getSalaryByCountry() {
        log.info("GET /api/analytics/by-country - Fetching salary by country");
        List<CountrySalaryResponse> countrySalaries = analyticsService.getSalaryByCountry();
        return ResponseEntity.ok(countrySalaries);
    }
}
