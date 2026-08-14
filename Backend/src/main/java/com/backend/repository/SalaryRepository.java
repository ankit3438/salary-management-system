package com.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.entity.Salary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    /**
     * Find the current salary for an employee (latest effective salary as of today)
     */
    @Query("SELECT s FROM Salary s WHERE s.employee.id = :employeeId " +
           "AND s.effectiveFrom <= CURRENT_DATE ORDER BY s.effectiveFrom DESC LIMIT 1")
    Optional<Salary> findCurrentSalaryByEmployeeId(@Param("employeeId") Long employeeId);

    /**
     * Find all salary records for an employee, ordered by effective date descending
     */
    @Query("SELECT s FROM Salary s WHERE s.employee.id = :employeeId ORDER BY s.effectiveFrom DESC")
    List<Salary> findSalaryHistoryByEmployeeId(@Param("employeeId") Long employeeId);

    /**
     * Paginated salary history for an employee
     */
    @Query("SELECT s FROM Salary s WHERE s.employee.id = :employeeId ORDER BY s.effectiveFrom DESC")
    Page<Salary> findSalaryHistoryByEmployeeIdPaginated(
            @Param("employeeId") Long employeeId,
            Pageable pageable);

    /**
     * Check if a salary with same employee and effective date exists
     */
    boolean existsByEmployeeIdAndEffectiveFrom(Long employeeId, LocalDate effectiveFrom);

    /**
     * Find salaries for analytics (current salaries only)
     */
    @Query("SELECT s FROM Salary s WHERE s.employee.id IN " +
           "(SELECT MAX(s2.id) FROM Salary s2 WHERE s2.effectiveFrom <= CURRENT_DATE " +
           "GROUP BY s2.employee.id)")
    List<Salary> findAllCurrentSalaries();
}
