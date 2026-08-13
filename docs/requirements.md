# Salary Management System — Requirements

## 1. Goal

Build a web-based salary management application for ACME, an organization with approximately 10,000 employees across multiple countries.

The current HR process is managed through Excel spreadsheets. The proposed system should provide HR managers with a centralized web application to manage employee salary information and answer useful questions about how the organization pays its employees.

## 2. Primary User

### HR Manager

The initial product is designed around the HR Manager persona specified in the assessment.

## 3. Scope

### In Scope

#### Employee Management
- View employees.
- Search employees.
- Filter employees by relevant attributes such as department and country.
- Paginate employee results.
- Create employee records.
- Update employee information.
- View employee details.

#### Salary Management
- View an employee's current salary.
- Add/update salary information.
- Store effective dates for salary changes.
- View salary history.
- Store salary currency.
- Store base salary and bonus information.

#### Salary Analytics
- Total employee count.
- Average salary.
- Median salary.
- Minimum and maximum salary.
- Salary distribution.
- Salary analysis by department.
- Salary analysis by country.

#### Data and Quality
- Seed the application with 10,000 employees.
- Validate API input.
- Provide consistent API error responses.
- Add meaningful unit tests.
- Add integration tests for important flows.
- Provide database migrations.
- Provide a deployable application.

## 4. Non-Functional Requirements

### Maintainability
The code should have clear separation of responsibilities, readable naming, and modular domain organization.

### Performance
The employee list must use pagination rather than returning all 10,000 employees in a single request. Analytics should use database-side aggregation where appropriate.

### Reliability
Invalid input should be rejected with clear errors. Important business operations should have automated tests.

### Usability
The UI should allow an HR Manager to complete the core employee and salary-management workflows without interacting directly with APIs.

## 5. Proposed User Flows

### View Employees
HR Manager opens Employees -> searches/filters -> views paginated results -> opens an employee.

### Update Salary
HR Manager opens an employee -> selects Update Salary -> enters salary information and effective date -> saves -> new salary becomes current and previous salary remains available in history.

### Analyze Salaries
HR Manager opens Dashboard -> views organization-level salary KPIs -> examines department/country breakdowns and salary distribution.

## 6. Deliberately Out of Scope

The assessment does not explicitly require the following, so they are excluded from the initial scope:

- Payroll processing.
- Tax calculation.
- PF/ESI calculation.
- Attendance management.
- Leave management.
- Payslip generation.
- Bank salary transfer.
- Employee self-service portal.
- Mobile application.
- Complex enterprise identity management.
- Microservice decomposition.

### Reasoning

The assessment focuses on salary data management, salary-related analysis, end-to-end functionality, engineering quality, testing, and product thinking. Adding unrelated HR/payroll capabilities would increase complexity without directly demonstrating the requested capabilities.

## 7. Success Criteria

The solution is considered successful when:

1. An HR Manager can manage employee records through the UI.
2. An HR Manager can update salary information.
3. Salary history is preserved.
4. An HR Manager can search and filter employees.
5. The dashboard provides useful salary analytics.
6. The system contains 10,000 seeded employees.
7. Core business logic has meaningful automated tests.
8. The application can be deployed and demonstrated.
9. The repository contains documentation explaining key engineering decisions.
