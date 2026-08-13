# Salary Management System — Database Design

## 1. Design Goals

The database should:
- Store employee information.
- Store current and historical salary information.
- Support salary analytics.
- Support multiple countries and currencies.
- Support efficient employee search/filter operations.
- Maintain salary history without destructive overwrites.

## 2. Entity Relationship

```text
+------------------+
|     employee     |
+------------------+
| id PK            |
| employee_code    |
| first_name       |
| last_name        |
| email            |
| department       |
| designation      |
| country          |
| joining_date     |
| status           |
| created_at       |
| updated_at       |
+--------+---------+
         |
         | 1
         |
         | N
         v
+------------------+
|      salary      |
+------------------+
| id PK            |
| employee_id FK   |
| base_salary      |
| bonus            |
| currency         |
| effective_from   |
| created_at       |
| created_by       |
+------------------+
```

## 3. Employee Table

| Column | Type | Notes |
|---|---|---|
| id | BIGINT | Primary key |
| employee_code | VARCHAR | Unique employee identifier |
| first_name | VARCHAR | Required |
| last_name | VARCHAR | Required |
| email | VARCHAR | Unique |
| department | VARCHAR | Employee department |
| designation | VARCHAR | Employee designation |
| country | VARCHAR | Employee country |
| joining_date | DATE | Joining date |
| status | VARCHAR | Employment status |
| created_at | TIMESTAMP | Creation timestamp |
| updated_at | TIMESTAMP | Last update timestamp |

## 4. Salary Table

| Column | Type | Notes |
|---|---|---|
| id | BIGINT | Primary key |
| employee_id | BIGINT | Foreign key to employee |
| base_salary | DECIMAL | Base salary |
| bonus | DECIMAL | Bonus amount |
| currency | VARCHAR | Currency code |
| effective_from | DATE | Date from which this salary applies |
| created_at | TIMESTAMP | Creation timestamp |
| created_by | VARCHAR | User/system that created the record |

## 5. Salary History Approach

Salary records are effective-dated rather than overwritten.

Example:

```text
Employee EMP001

2024-01-01 -> 800000
2025-01-01 -> 1000000
2026-01-01 -> 1200000
```

The latest applicable record represents the current salary, while older records remain available for history.

This design supports the HR use case of reviewing salary changes over time.

## 6. Indexing Strategy

Indexes should be based on expected query patterns.

Potential indexes:

```sql
CREATE UNIQUE INDEX idx_employee_code
ON employee(employee_code);

CREATE UNIQUE INDEX idx_employee_email
ON employee(email);

CREATE INDEX idx_employee_department
ON employee(department);

CREATE INDEX idx_employee_country
ON employee(country);

CREATE INDEX idx_salary_employee_effective
ON salary(employee_id, effective_from);
```

The exact indexes should be verified against actual query plans rather than added indiscriminately.

## 7. Data Integrity

Recommended constraints:
- Unique employee code.
- Unique employee email.
- Non-null required employee fields.
- Non-negative salary amounts.
- Foreign key from salary to employee.
- Valid effective dates.

## 8. Migrations

Flyway migrations:

```text
V1__create_employee_table.sql
V2__create_salary_table.sql
V3__create_indexes.sql
```

All schema changes should be version-controlled.

## 9. Seed Data

A dedicated seed script will generate approximately 10,000 employees with:
- Multiple countries.
- Multiple departments.
- Multiple designations.
- Different salary ranges.
- Different currencies.
- Joining dates.
- Salary history.

Seed data should be deterministic where practical so that demos and tests are reproducible.
