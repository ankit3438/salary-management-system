# Salary Management System — Architecture

## 1. Architecture Overview

### Proposed Architecture

The solution uses a **modular monolith** for the backend rather than multiple microservices.

```text
                    Angular UI
                        |
                     REST API
                        |
              +---------+---------+
              |   Spring Boot    |
              | Modular Monolith |
              +---------+---------+
                        |
        +---------------+---------------+
        |               |               |
   Employee Module  Salary Module  Analytics Module
        |               |               |
        +---------------+---------------+
                        |
                    PostgreSQL
```

The assessment requires an end-to-end backend and UI, a relational database, seeded data, testing, deployment, and supporting engineering artifacts.

## 2. Backend Modules

### Employee Module
Responsible for:
- Employee creation.
- Employee updates.
- Employee retrieval.
- Search.
- Filtering.
- Pagination.

### Salary Module
Responsible for:
- Salary creation/update.
- Current salary retrieval.
- Salary history.
- Effective-dated salary records.

### Analytics Module
Responsible for:
- Organization-level salary KPIs.
- Department-level salary analysis.
- Country-level salary analysis.
- Salary distribution.

## 3. Why a Modular Monolith?

The assessment does not require microservices. The target population is approximately 10,000 employees, and the problem is centered around one closely related business domain.

A modular monolith reduces operational complexity while still allowing clear separation of domain responsibilities.

This is a deliberate trade-off rather than a limitation. If future requirements introduce independent scaling, deployment, ownership, or availability needs, individual modules could be extracted into services.

## 4. Technology Proposal

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Bean Validation

### Database
- PostgreSQL
- Flyway for schema migrations

### Frontend
- Angular
- Angular Material or another suitable component library

### Testing
- JUnit 5
- Mockito
- Spring Boot test support
- Testcontainers for selected integration tests

### Deployment
- Docker
- Docker Compose for local development
- Cloud deployment suitable for the assessment

## 5. Backend Layering

```text
Controller
    |
    v
Service
    |
    v
Repository
    |
    v
PostgreSQL
```

Controllers handle HTTP concerns, services contain business logic, repositories handle persistence, and DTOs prevent persistence entities from becoming the API contract.

## 6. Frontend Structure

```text
Angular
 |
 +-- Dashboard
 |
 +-- Employees
 |     +-- Employee List
 |     +-- Employee Details
 |     +-- Employee Form
 |
 +-- Salary
       +-- Salary Update
       +-- Salary History
```

## 7. API Communication

The frontend communicates with Spring Boot through REST APIs.

Example:

```text
Angular
   |
   | GET /api/employees?page=0&size=20
   v
EmployeeController
   |
EmployeeService
   |
EmployeeRepository
   |
PostgreSQL
```

## 8. Key Architectural Principles

- Keep business logic in services rather than controllers.
- Use DTOs at API boundaries.
- Validate incoming requests.
- Keep database migrations version-controlled.
- Use pagination for employee listing.
- Prefer database-side aggregation for analytics.
- Avoid unnecessary infrastructure unless a requirement justifies it.

## 9. Future Evolution

Potential future additions include:
- Authentication and role-based authorization.
- Audit logging.
- Advanced reporting/export.
- Payroll integration.
- Independent service deployment if scale or organizational requirements justify it.
