package com.backend.Services;

import java.util.List;

import com.backend.dto.CountrySalaryResponse;
import com.backend.dto.DashboardSummaryResponse;
import com.backend.dto.DepartmentSalaryResponse;
import com.backend.dto.SalarySummaryResponse;

public interface AnalyticsService {

    SalarySummaryResponse getSalarySummary();

    List<DepartmentSalaryResponse> getSalaryByDepartment();

    List<CountrySalaryResponse> getSalaryByCountry();

    DashboardSummaryResponse getDashboardSummary();
}
