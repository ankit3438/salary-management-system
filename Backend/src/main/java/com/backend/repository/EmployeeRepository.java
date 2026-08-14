package com.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.entity.Employee;

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
}
