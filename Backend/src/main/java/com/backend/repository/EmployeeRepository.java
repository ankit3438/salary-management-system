package com.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.entity.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeCode(String employeeCode);

    Optional<Employee> findByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE " +
            "(:search IS NULL OR LOWER(e.firstName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(e.employeeCode) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:department IS NULL OR e.department = :department) " +
            "AND (:country IS NULL OR e.country = :country) " +
            "AND (:status IS NULL OR e.status = :status)")
    Page<Employee> findByCriteria(
            @Param("search") String search,
            @Param("department") String department,
            @Param("country") String country,
            @Param("status") String status,
            Pageable pageable);

    Page<Employee> findByDepartment(String department, Pageable pageable);

    Page<Employee> findByCountry(String country, Pageable pageable);

    long countByStatus(String status);

    @Query("SELECT DISTINCT e.department FROM Employee e " +
            "WHERE e.department IS NOT NULL AND e.department <> '' " +
            "ORDER BY e.department")
    List<String> findDistinctDepartments();

    @Query("SELECT DISTINCT e.country FROM Employee e " +
            "WHERE e.country IS NOT NULL AND e.country <> '' " +
            "ORDER BY e.country")
    List<String> findDistinctCountries();
}
