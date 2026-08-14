# MySQL Configuration Guide

## Overview

This project uses **MySQL 8.0+** as the relational database for storing employee and salary information. Database schema management is handled by **Flyway**, which automatically runs SQL migrations on application startup.

## Prerequisites

- MySQL 8.0 or higher installed and running
- MySQL client installed (for manual schema inspection if needed)
- Maven and Java 17+ configured

## Initial Setup

### Step 1: Create the Database

Connect to MySQL and create the salary management system database:

```sql
-- Connect to MySQL
mysql -u root -p

-- Create the database
CREATE DATABASE salary_management_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Verify creation
SHOW DATABASES;
```

### Step 2: Configure Application Properties

Edit `Backend/src/main/resources/application.properties` and ensure the following MySQL configuration is present:

```properties
# MySQL Database Connection
spring.datasource.url=jdbc:mysql://localhost:3306/salary_management_system?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate/JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# Flyway Configuration
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true
```

**Configuration Explanation:**
- `spring.datasource.url`: MySQL connection string pointing to the salary management database
- `spring.datasource.username`: MySQL username (default: root)
- `spring.datasource.password`: MySQL password (change as per your setup)
- `spring.jpa.database-platform`: Tells Hibernate to use MySQL 8 dialect
- `spring.jpa.hibernate.ddl-auto=validate`: Validates schema but doesn't modify it (Flyway handles migrations)
- `spring.flyway.locations`: Location of migration SQL files
- `spring.flyway.baseline-on-migrate`: Creates baseline for existing schemas if needed

### Step 3: Database Migrations

Database schema is managed through Flyway migrations located in:
```
Backend/src/main/resources/db/migration/
```

**Initial Migration Files:**
- `V1__Initial_Schema.sql` - Creates `employee` and `salary` tables with proper indexing

**Naming Convention:**
- `V{version}__{description}.sql` - e.g., `V2__Add_employee_status_history.sql`
- Versions must be sequential and unique
- Only `.sql` files are processed by Flyway

### Step 4: Run the Application

```bash
# Navigate to Backend directory
cd Backend

# Run the application using Maven
./mvnw spring-boot:run

# Or build and run the JAR
./mvnw clean package
java -jar target/Backend-0.0.1-SNAPSHOT.jar
```

**What happens on startup:**
1. Spring Boot connects to MySQL
2. Flyway checks for pending migrations
3. Pending migrations are executed in version order
4. Application starts and is ready for requests

## Database Schema

### Employee Table

| Column | Type | Constraints | Purpose |
|--------|------|-----------|---------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique employee identifier |
| employee_code | VARCHAR(50) | UNIQUE, NOT NULL | Business-friendly employee code |
| first_name | VARCHAR(100) | NOT NULL | Employee's first name |
| last_name | VARCHAR(100) | NOT NULL | Employee's last name |
| email | VARCHAR(100) | UNIQUE, NOT NULL | Employee's email |
| department | VARCHAR(100) | | Department assignment |
| designation | VARCHAR(100) | | Job designation |
| country | VARCHAR(50) | | Employee's country |
| joining_date | DATE | | Date employee joined |
| status | VARCHAR(50) | DEFAULT 'ACTIVE' | Employment status (ACTIVE, INACTIVE, etc.) |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation timestamp |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP ON UPDATE | Record last update timestamp |

**Indexes:**
- `employee_code`, `email`, `status`, `department`, `country`, `created_at`

### Salary Table

| Column | Type | Constraints | Purpose |
|--------|------|-----------|---------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique salary record identifier |
| employee_id | BIGINT | FOREIGN KEY, NOT NULL | References employee(id) |
| base_salary | DECIMAL(15,2) | NOT NULL | Base salary amount |
| bonus | DECIMAL(15,2) | | Bonus amount |
| currency | VARCHAR(10) | DEFAULT 'USD' | Salary currency code |
| effective_from | DATE | NOT NULL | Date from which salary is effective |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation timestamp |
| created_by | VARCHAR(100) | | User/system that created the record |

**Constraints:**
- Foreign key constraint to employee table (CASCADE delete/update)
- Unique constraint on (employee_id, effective_from) to prevent duplicate effective dates

**Indexes:**
- `employee_id`, `effective_from`, `created_at`

## Adding New Migrations

To add a new migration (e.g., adding a column):

1. Create a new SQL file in `Backend/src/main/resources/db/migration/`
2. Follow naming convention: `V{next_version}__{description}.sql`
3. Write the migration SQL
4. Restart the application (Flyway will execute it automatically)

**Example:**
```sql
-- V2__Add_employee_phone.sql
ALTER TABLE employee ADD COLUMN phone_number VARCHAR(20);
```

## Troubleshooting

### Connection Error: "Access denied for user 'root'@'localhost'"
- Verify MySQL is running
- Check username and password in `application.properties`
- Ensure user has sufficient privileges

### Error: "Unknown database 'salary_management_system'"
- Create the database using the SQL command provided in Step 1

### Flyway Migration Error: "Migration checksum validation failed"
- Do not modify migration files after they've been run
- Create a new migration file instead

### Port Already in Use
- MySQL default port is 3306. If in use, specify a different port:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3307/salary_management_system
  ```

## Environment-Specific Configuration

For different environments, create property files:

```properties
# Production
application-prod.properties
spring.datasource.url=jdbc:mysql://prod-server:3306/salary_management_system
spring.datasource.username=prod_user

# Development (default)
application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/salary_management_system

# Testing
application-test.properties
spring.datasource.url=jdbc:mysql://localhost:3306/salary_management_system_test
```

Run with specific profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```

## References

- [Flyway Documentation](https://flywaydb.org/documentation/)
- [MySQL Connector/J](https://dev.mysql.com/doc/connector-j/en/)
- [Spring Boot Database Configuration](https://spring.io/guides/gs/accessing-data-jpa/)
- [Hibernate ORM](https://hibernate.org/orm/)
- [MySQL 8.0 Documentation](https://dev.mysql.com/doc/refman/8.0/en/)
