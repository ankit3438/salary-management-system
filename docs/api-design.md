# Salary Management System — API Design

## 1. API Conventions

Base path:

```text
/api
```

Use JSON for request and response payloads.

Use HTTP status codes consistently:
- 200 OK
- 201 Created
- 204 No Content where appropriate
- 400 Bad Request
- 404 Not Found
- 409 Conflict
- 500 Internal Server Error

## 2. Employee APIs

### List Employees

```http
GET /api/employees?page=0&size=20
```

Optional filters:

```http
GET /api/employees?page=0&size=20&search=ankit
GET /api/employees?page=0&size=20&department=Engineering
GET /api/employees?page=0&size=20&country=India
```

Response should contain paginated data and metadata.

### Get Employee

```http
GET /api/employees/{id}
```

### Create Employee

```http
POST /api/employees
```

Example request:

```json
{
  "employeeCode": "EMP001",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "department": "Engineering",
  "designation": "Software Engineer",
  "country": "India",
  "joiningDate": "2024-01-10"
}
```

### Update Employee

```http
PUT /api/employees/{id}
```

## 3. Salary APIs

### Add Salary Record

```http
POST /api/employees/{id}/salary
```

Example:

```json
{
  "baseSalary": 1200000,
  "bonus": 200000,
  "currency": "INR",
  "effectiveFrom": "2026-09-01"
}
```

### Get Current Salary

```http
GET /api/employees/{id}/salary
```

### Get Salary History

```http
GET /api/employees/{id}/salary/history
```

## 4. Analytics APIs

### Summary

```http
GET /api/analytics/summary
```

Possible response:

```json
{
  "totalEmployees": 10000,
  "averageSalary": 825000,
  "medianSalary": 760000,
  "minimumSalary": 180000,
  "maximumSalary": 3500000
}
```

### Salary by Department

```http
GET /api/analytics/by-department
```

### Salary by Country

```http
GET /api/analytics/by-country
```

### Salary Distribution

```http
GET /api/analytics/salary-distribution
```

## 5. Error Response

Use a consistent structure:

```json
{
  "timestamp": "2026-08-13T12:00:00Z",
  "status": 404,
  "error": "EMPLOYEE_NOT_FOUND",
  "message": "Employee EMP001 does not exist",
  "path": "/api/employees/EMP001"
}
```

## 6. API Design Principles

- Use nouns rather than verbs in resource URLs.
- Use appropriate HTTP methods.
- Paginate collection endpoints.
- Validate request payloads.
- Do not expose JPA entities directly as API contracts.
- Keep error responses consistent.
- Keep analytics aggregation on the backend/database side.
