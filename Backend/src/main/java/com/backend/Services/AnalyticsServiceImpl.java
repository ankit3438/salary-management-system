package com.backend.Services;

import com.backend.dto.CountrySalaryResponse;
import com.backend.dto.DashboardSummaryResponse;
import com.backend.dto.DepartmentSalaryResponse;
import com.backend.dto.SalarySummaryResponse;
import com.backend.entity.Salary;
import com.backend.repository.EmployeeRepository;
import com.backend.repository.SalaryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AnalyticsServiceImpl implements AnalyticsService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public SalarySummaryResponse getSalarySummary() {
        log.info("Calculating salary summary");

        var allCurrentSalaries = salaryRepository.findAllCurrentSalaries();

        if (allCurrentSalaries.isEmpty()) {
            return SalarySummaryResponse.builder()
                    .totalEmployees(0L)
                    .averageSalary(BigDecimal.ZERO)
                    .medianSalary(BigDecimal.ZERO)
                    .minimumSalary(BigDecimal.ZERO)
                    .maximumSalary(BigDecimal.ZERO)
                    .build();
        }

        List<BigDecimal> baseSalaries = allCurrentSalaries.stream()
                .map(s -> s.getBaseSalary().add(s.getBonus()))
                .sorted()
                .collect(Collectors.toList());

        BigDecimal averageSalary = baseSalaries.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(baseSalaries.size()), 2, java.math.RoundingMode.HALF_UP);

        BigDecimal medianSalary = calculateMedian(baseSalaries);
        BigDecimal minimumSalary = baseSalaries.get(0);
        BigDecimal maximumSalary = baseSalaries.get(baseSalaries.size() - 1);

        return SalarySummaryResponse.builder()
                .totalEmployees((long) baseSalaries.size())
                .averageSalary(averageSalary)
                .medianSalary(medianSalary)
                .minimumSalary(minimumSalary)
                .maximumSalary(maximumSalary)
                .build();
    }

    @Override
    public List<DepartmentSalaryResponse> getSalaryByDepartment() {
        log.info("Calculating salary by department");

        var allCurrentSalaries = salaryRepository.findAllCurrentSalaries();

        return allCurrentSalaries.stream()
                .collect(Collectors.groupingBy(s -> s.getEmployee().getDepartment()))
                .entrySet().stream()
                .map(entry -> {
                    String department = entry.getKey();
                    List<BigDecimal> salaries = entry.getValue().stream()
                            .map(s -> s.getBaseSalary().add(s.getBonus()))
                            .sorted()
                            .collect(Collectors.toList());

                    BigDecimal averageSalary = salaries.stream()
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                            .divide(new BigDecimal(salaries.size()), 2, java.math.RoundingMode.HALF_UP);

                    return DepartmentSalaryResponse.builder()
                            .department(department)
                            .employeeCount((long) salaries.size())
                            .averageSalary(averageSalary)
                            .medianSalary(calculateMedian(salaries))
                            .minimumSalary(salaries.get(0))
                            .maximumSalary(salaries.get(salaries.size() - 1))
                            .build();
                })
                .sorted(Comparator.comparing(DepartmentSalaryResponse::getDepartment))
                .collect(Collectors.toList());
    }

    @Override
    public List<CountrySalaryResponse> getSalaryByCountry() {
        log.info("Calculating salary by country");

        var allCurrentSalaries = salaryRepository.findAllCurrentSalaries();

        return allCurrentSalaries.stream()
                .collect(Collectors.groupingBy(s -> s.getEmployee().getCountry()))
                .entrySet().stream()
                .map(entry -> {
                    String country = entry.getKey();
                    List<BigDecimal> salaries = entry.getValue().stream()
                            .map(s -> s.getBaseSalary().add(s.getBonus()))
                            .sorted()
                            .collect(Collectors.toList());

                    BigDecimal averageSalary = salaries.stream()
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                            .divide(new BigDecimal(salaries.size()), 2, java.math.RoundingMode.HALF_UP);

                    return CountrySalaryResponse.builder()
                            .country(country)
                            .employeeCount((long) salaries.size())
                            .averageSalary(averageSalary)
                            .medianSalary(calculateMedian(salaries))
                            .minimumSalary(salaries.get(0))
                            .maximumSalary(salaries.get(salaries.size() - 1))
                            .build();
                })
                .sorted(Comparator.comparing(CountrySalaryResponse::getCountry))
                .collect(Collectors.toList());
    }

    private BigDecimal calculateMedian(List<BigDecimal> salaries) {
        int size = salaries.size();
        if (size % 2 == 0) {
            return salaries.get(size / 2 - 1)
                    .add(salaries.get(size / 2))
                    .divide(new BigDecimal(2), 2, java.math.RoundingMode.HALF_UP);
        } else {
            return salaries.get(size / 2);
        }
    }

    @Override
public DashboardSummaryResponse getDashboardSummary() {
    long totalEmployees = employeeRepository.count();
    long activeEmployees = employeeRepository.countByStatus("ACTIVE");

    var salaries = salaryRepository.findAllCurrentSalaries();

    Map<String, BigDecimal> totalPayrollByCurrency = salaries.stream()
        .collect(Collectors.groupingBy(
            Salary::getCurrency,
            Collectors.reducing(BigDecimal.ZERO,
                s -> s.getBaseSalary().add(s.getBonus()),
                BigDecimal::add)
        ));

    Map<String, Double> averageSalaryByCurrency = new HashMap<>();
    totalPayrollByCurrency.forEach((currency, total) -> {
        long count = salaries.stream().filter(s -> s.getCurrency().equals(currency)).count();
        averageSalaryByCurrency.put(currency, count > 0 ? total.doubleValue() / count : 0.0);
    });

    return DashboardSummaryResponse.builder()
            .totalEmployees(totalEmployees)
            .activeEmployees(activeEmployees)
            .totalPayrollByCurrency(totalPayrollByCurrency)
            .averageSalaryByCurrency(averageSalaryByCurrency)
            .build();
}

}
