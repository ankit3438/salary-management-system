package com.backend.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.backend.dto.SalaryRequest;
import com.backend.dto.SalaryResponse;

import java.util.List;

public interface SalaryService {

    SalaryResponse addSalary(Long employeeId, SalaryRequest request);

    SalaryResponse getCurrentSalary(Long employeeId);

    List<SalaryResponse> getSalaryHistory(Long employeeId);

    Page<SalaryResponse> getSalaryHistoryPaginated(Long employeeId, Pageable pageable);

    SalaryResponse getSalaryById(Long salaryId);
}
