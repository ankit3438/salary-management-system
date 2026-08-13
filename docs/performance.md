# Salary Management System — Performance Considerations

## 1. Expected Dataset

The assessment requires a seed dataset of approximately 10,000 employees.

This is large enough to expose inefficient list and query patterns, but not large enough to justify distributed infrastructure by itself.

## 2. Employee Pagination

Never load all employees into the UI at once.

Use:

```http
GET /api/employees?page=0&size=20
```

Spring Data pagination should be used at the repository/service boundary.

## 3. Search and Filtering

Search and filtering should be performed by the database rather than fetching all records and filtering in Java.

Examples:
- Department.
- Country.
- Employee code.
- Name.
- Email.

## 4. Database Indexes

Indexes should support common access patterns such as:
- Employee code.
- Email.
- Department.
- Country.
- Employee ID + salary effective date.

Indexes should be validated using actual query plans.

## 5. Analytics

Salary analytics should preferably use database aggregation rather than retrieving every salary record into application memory.

Examples:

```sql
AVG(base_salary)
MIN(base_salary)
MAX(base_salary)
COUNT(*)
GROUP BY department
GROUP BY country
```

## 6. N+1 Query Prevention

When retrieving employee data with related salary information, avoid patterns that cause one query for employees followed by one query per employee.

Use appropriate:
- Fetch strategies.
- Projections.
- Join queries.
- Batch fetching where justified.

The exact solution should be selected based on the endpoint's data requirements.

## 7. API Response Size

Keep API responses focused on what the UI needs.

Do not return unnecessary entity graphs or salary history for every employee in a paginated list.

## 8. Seed Performance

The seed process should avoid inserting 10,000 records through inefficient individual application-level transactions where a bulk approach is more appropriate.

Measure the seed process and document the approach.

## 9. Frontend Performance

The UI should:
- Use server-side pagination.
- Avoid rendering thousands of rows at once.
- Load dashboard data through focused APIs.
- Avoid unnecessary repeated API calls.

## 10. Performance Verification

Before submission, verify:
- Employee list response time.
- Search/filter response time.
- Dashboard query performance.
- Seed execution time.
- Database query plans for important queries.

Performance claims should be based on measurements rather than assumptions.
