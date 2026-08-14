-- Initial Database Schema for Salary Management System
-- Database: salary_management_system
-- Dialect: MySQL 8.0+

-- Create employee table
CREATE TABLE employee (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employee_code VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department VARCHAR(100),
    designation VARCHAR(100),
    country VARCHAR(50),
    joining_date DATE,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_employee_code (employee_code),
    INDEX idx_email (email),
    INDEX idx_status (status),
    INDEX idx_department (department),
    INDEX idx_country (country),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create salary table
CREATE TABLE salary (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    base_salary DECIMAL(15, 2) NOT NULL,
    bonus DECIMAL(15, 2),
    currency VARCHAR(10) NOT NULL DEFAULT 'USD',
    effective_from DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    
    CONSTRAINT fk_salary_employee FOREIGN KEY (employee_id) 
        REFERENCES employee(id) ON DELETE CASCADE ON UPDATE CASCADE,
    
    INDEX idx_employee_id (employee_id),
    INDEX idx_effective_from (effective_from),
    INDEX idx_created_at (created_at),
    UNIQUE KEY unique_employee_effective_date (employee_id, effective_from)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
