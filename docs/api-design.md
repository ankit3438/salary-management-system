# API Design Documentation

## 1. Overview

This document describes the REST API design for the Employee Management application based on the controllers currently implemented.

### Base URL

```text
/api
```

### API Style

- RESTful HTTP endpoints
- JSON request/response payloads
- Path variables for resource identification
- Query parameters for filtering and pagination
- HTTP status codes to represent operation results
- Bean Validation for request DTOs

---

# 2. Employee APIs

Base path:

```text
/api/employees
```

## 2.1 Get All Employees

### Endpoint

```http
GET /api/employees
```

### Query Parameters

| Parameter | Required | Default | Description |
|---|---|---:|---|
| `page` | No | `0` | Zero-based page number |
| `size` | No | `20` | Number of employees per page |

### Example

```http
GET /api/employees?page=0&size=20
```

### Response

**HTTP 200 OK**

Returns a paginated list of employees.

---

## 2.2 Search Employees

### Endpoint

```http
GET /api/employees/search
```

### Query Parameters

| Parameter | Required | Default | Description |
|---|---|---:|---|
| `search` | No | - | General employee search criteria |
| `department` | No | - | Filter by department |
| `country` | No | - | Filter by country |
| `status` | No | - | Filter by employee status |
| `page` | No | `0` | Zero-based page number |
| `size` | No | `20` | Number of employees per page |

### Example

```http
GET /api/employees/search?search=Ankit&department=IT&country=India&status=ACTIVE&page=0&size=20
```

### Response

**HTTP 200 OK**

Returns a paginated list of matching employees.

---

## 2.3 Get Employee Filters

### Endpoint

```http
GET /api/employees/filters
```

### Response

**HTTP 200 OK**

Returns available employee filter values.

---

## 2.4 Get Employee By ID

### Endpoint

```http
GET /api/employees/{id}
```

### Path Parameters

| Parameter | Required | Description |
|---|---|---|
| `id` | Yes | Employee ID |

### Example

```http
GET /api/employees/1
```

### Response

**HTTP 200 OK**

Returns the employee associated with the supplied ID.

---

## 2.5 Get Employee By Employee Code

### Endpoint

```http
GET /api/employees/code/{employeeCode}
```

### Path Parameters

| Parameter | Required | Description |
|---|---|---|
| `employeeCode` | Yes | Unique employee code |

### Example

```http
GET /api/employees/code/EMP001
```

### Response

**HTTP 200 OK**

Returns the employee associated with the supplied employee code.

---

## 2.6 Create Employee

### Endpoint

```http
POST /api/employees
```

### Headers

```http
Content-Type: application/json
```

### Request Body

Request body uses `EmployeeRequest`.

Example structure:

```json
{
  "employeeCode": "EMP001",
  "firstName": "Ankit",
  "lastName": "Jha",
  "email": "ankit@example.com"
}
```

> The exact required fields and validation rules are defined by `EmployeeRequest`.

### Response

**HTTP 201 Created**

Returns the newly created employee as `EmployeeResponse`.

---

## 2.7 Update Employee

### Endpoint

```http
PUT /api/employees/{id}
```

### Path Parameters

| Parameter | Required | Description |
|---|---|---|
| `id` | Yes | Employee ID |

### Headers

```http
Content-Type: application/json
```

### Request Body

Uses `EmployeeRequest`.

### Response

**HTTP 200 OK**

Returns the updated employee as `EmployeeResponse`.

---

## 2.8 Delete Employee

### Endpoint

```http
DELETE /api/employees/{id}
```

### Path Parameters

| Parameter | Required | Description |
|---|---|---|
| `id` | Yes | Employee ID |

### Response

**HTTP 204 No Content**

The employee was successfully deleted.

---

# 3. Salary APIs

Base path:

```text
/api/employees/{employeeId}/salary
```

Salary APIs are nested under an employee because salary records belong to a specific employee.

---

## 3.1 Add Salary

### Endpoint

```http
POST /api/employees/{employeeId}/salary
```

### Path Parameters

| Parameter | Required | Description |
|---|---|---|
| `employeeId` | Yes | Employee ID |

### Headers

```http
Content-Type: application/json
```

### Request Body

Uses `SalaryRequest`.

Current request model:

```json
{
  "baseSalary": 75000,
  "bonus": 10000,
  "currency": "INR",
  "effectiveFrom": "2026-08-15"
}
```

### Validation Rules

| Field | Rule |
|---|---|
| `baseSalary` | Required and must be greater than `0` |
| `bonus` | Optional; cannot be negative |
| `currency` | Optional |
| `effectiveFrom` | Required |

### Response

**HTTP 201 Created**

Returns the created salary as `SalaryResponse`.

---

## 3.2 Get Current Salary

### Endpoint

```http
GET /api/employees/{employeeId}/salary
```

### Path Parameters

| Parameter | Required | Description |
|---|---|---|
| `employeeId` | Yes | Employee ID |

### Response

**HTTP 200 OK**

Returns the employee's current salary.

---

## 3.3 Get Salary History

### Endpoint

```http
GET /api/employees/{employeeId}/salary/history
```

### Path Parameters

| Parameter | Required | Description |
|---|---|---|
| `employeeId` | Yes | Employee ID |

### Response

**HTTP 200 OK**

Returns a list of salary records for the employee.

---

## 3.4 Get Paginated Salary History

### Endpoint

```http
GET /api/employees/{employeeId}/salary/history/paginated
```

### Path Parameters

| Parameter | Required | Description |
|---|---|---|
| `employeeId` | Yes | Employee ID |

### Pageable Parameters

The endpoint uses Spring's `Pageable`.

Default configuration:

```text
page = 0
size = 10
sort = effectiveFrom,DESC
```

### Example

```http
GET /api/employees/1/salary/history/paginated?page=0&size=10&sort=effectiveFrom,DESC
```

### Response

**HTTP 200 OK**

Returns a Spring `Page<SalaryResponse>`.

---

## 3.5 Get Salary By ID

### Endpoint

```http
GET /api/employees/{employeeId}/salary/{salaryId}
```

### Path Parameters

| Parameter | Required | Description |
|---|---|---|
| `employeeId` | Yes | Employee ID |
| `salaryId` | Yes | Salary record ID |

### Example

```http
GET /api/employees/1/salary/10
```

### Response

**HTTP 200 OK**

Returns the salary record identified by `salaryId`.

---

# 4. Analytics APIs

Base path:

```text
/api/analytics
```

Analytics endpoints provide aggregated information for dashboards and reporting.

---

## 4.1 Dashboard Summary

### Endpoint

```http
GET /api/analytics/summary
```

### Response

**HTTP 200 OK**

Returns dashboard summary information using `DashboardSummaryResponse`.

---

## 4.2 Salary By Department

### Endpoint

```http
GET /api/analytics/by-department
```

### Response

**HTTP 200 OK**

Returns salary aggregation grouped by department.

Response type:

```text
List<DepartmentSalaryResponse>
```

---

## 4.3 Salary By Country

### Endpoint

```http
GET /api/analytics/by-country
```

### Response

**HTTP 200 OK**

Returns salary aggregation grouped by country.

Response type:

```text
List<CountrySalaryResponse>
```

---

# 5. API Summary

| Method | Endpoint | Purpose | Success |
|---|---|---|---|
| GET | `/api/employees` | Get employees | 200 |
| GET | `/api/employees/search` | Search/filter employees | 200 |
| GET | `/api/employees/filters` | Get employee filters | 200 |
| GET | `/api/employees/{id}` | Get employee by ID | 200 |
| GET | `/api/employees/code/{employeeCode}` | Get employee by code | 200 |
| POST | `/api/employees` | Create employee | 201 |
| PUT | `/api/employees/{id}` | Update employee | 200 |
| DELETE | `/api/employees/{id}` | Delete employee | 204 |
| POST | `/api/employees/{employeeId}/salary` | Add salary | 201 |
| GET | `/api/employees/{employeeId}/salary` | Get current salary | 200 |
| GET | `/api/employees/{employeeId}/salary/history` | Get salary history | 200 |
| GET | `/api/employees/{employeeId}/salary/history/paginated` | Get paginated salary history | 200 |
| GET | `/api/employees/{employeeId}/salary/{salaryId}` | Get salary by ID | 200 |
| GET | `/api/analytics/summary` | Get dashboard summary | 200 |
| GET | `/api/analytics/by-department` | Salary by department | 200 |
| GET | `/api/analytics/by-country` | Salary by country | 200 |

---

# 6. HTTP Status Code Convention

The current controllers use the following success status codes:

| Status | Meaning | Usage |
|---|---|---|
| `200 OK` | Request successful | GET, successful PUT |
| `201 Created` | Resource created | POST |
| `204 No Content` | Operation successful with no response body | DELETE |
| `400 Bad Request` | Invalid request/validation failure | `@Valid` request failures |
| `404 Not Found` | Resource not found | Typically handled by service/exception layer |
| `500 Internal Server Error` | Unexpected server error | Global exception handling |

---

# 7. Validation

The API uses Jakarta Bean Validation for request DTOs.

For example, `SalaryRequest` contains:

```java
@NotNull
@DecimalMin(value = "0.0", inclusive = false)
private BigDecimal baseSalary;

@DecimalMin(value = "0.0")
private BigDecimal bonus;

@NotNull
private LocalDate effectiveFrom;
```

Therefore an invalid salary request such as:

```json
{
  "baseSalary": 0,
  "bonus": -1000,
  "effectiveFrom": null
}
```

should result in:

```http
400 Bad Request
```

The service layer should not be invoked when controller-level validation fails.

---

# 8. Pagination and Sorting

The employee APIs implement pagination using explicit query parameters:

```text
page
size
```

Employees are sorted by:

```text
id DESC
```

Example:

```http
GET /api/employees?page=0&size=20
```

Salary history uses Spring `Pageable` with the following defaults:

```text
page = 0
size = 10
sort = effectiveFrom,DESC
```

---

# 9. Resource Relationships

The API follows a nested-resource design for salaries:

```text
Employee
   |
   └── Salary
        |
        ├── Current Salary
        ├── Salary History
        └── Individual Salary Record
```

Therefore salary resources are accessed through:

```text
/api/employees/{employeeId}/salary
```

This makes the ownership relationship explicit.

---

# 10. API Design Principles Used

### Resource-oriented URLs

The API uses nouns rather than HTTP verbs in URLs.

Good:

```text
GET /api/employees/1
```

rather than:

```text
GET /api/getEmployee/1
```

### HTTP methods represent operations

```text
GET     → Read
POST    → Create
PUT     → Update
DELETE  → Delete
```

### Query parameters for search and pagination

Filtering and pagination are represented using query parameters:

```text
/api/employees/search?department=IT&page=0&size=20
```

### Path variables for resource identity

```text
/api/employees/{id}
/api/employees/{employeeId}/salary/{salaryId}
```

### Appropriate HTTP status codes

```text
201 → resource created
200 → successful read/update
204 → successful deletion
400 → invalid request
```

---

# 11. Controller Inventory

The application currently exposes three controller resources:

```text
/api/employees
        |
        ├── Employee CRUD
        └── Employee search/filtering
        |
        └── /{employeeId}/salary
                |
                ├── Add salary
                ├── Current salary
                ├── Salary history
                ├── Paginated salary history
                └── Salary by ID

/api/analytics
        |
        ├── Dashboard summary
        ├── Salary by department
        └── Salary by country
```

This design separates:

1. **Employee management**
2. **Employee salary management**
3. **Analytics/reporting**
