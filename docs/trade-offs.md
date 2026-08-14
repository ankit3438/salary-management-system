# Salary Management System — Trade-offs and Decisions

## 1. Modular Monolith vs Microservices

### Decision
Use a modular monolith.

### Why
The assessment targets approximately 10,000 employees and does not require independent service scaling or deployment.

### Benefit
Lower operational complexity and faster delivery while keeping clear domain boundaries.

### Future
Modules can be extracted into services if real requirements justify it.

---

## 2. MySQL vs NoSQL

### Decision
Use MySQL.

### Why
The problem is relational:
- Employees have salary records.
- Salary history belongs to employees.
- Analytics require aggregations.
- Referential integrity is useful.

MySQL is a reliable relational database that is widely used, cost-effective, and explicitly allowed by the assessment.

---

## 3. Salary Overwrite vs Salary History

### Decision
Store a new effective-dated salary record instead of overwriting the previous salary.

### Why
HR needs historical visibility. Destructive updates would make it difficult to understand how salary changed over time.

---

## 4. Return All Employees vs Pagination

### Decision
Use pagination.

### Why
The system has 10,000 employees. Returning the entire collection unnecessarily increases response size, memory usage, and UI work.

---

## 5. Separate Salary History Table vs Effective-Dated Salary Records

### Decision
Use effective-dated salary records initially.

### Why
A salary record already represents a point-in-time compensation state. A separate history table would duplicate data unless additional audit requirements emerge.

---

## 6. Authentication

### Decision
Keep full authentication outside the initial MVP.

### Why
Authentication is not explicitly required by the assessment. The focus is salary management, analytics, engineering quality, and product thinking.

### Production Consideration
A production system should use authentication and role-based authorization because salary information is sensitive.

---

## 7. Payroll Processing

### Decision
Exclude payroll processing.

### Why
The assessment asks for salary management and questions about how the organization pays people, not full payroll calculation, taxation, attendance, or bank transfer processing.

---

## 8. AI-Generated Code

### Decision
Use AI as an accelerator, not as an authority.

### Approach
AI may help with:
- Boilerplate.
- Test case generation.
- Code review.
- Edge-case discovery.
- Documentation.

Generated code will be reviewed, tested, and modified manually.

---

## 9. Complexity vs Delivery

### Decision
Prefer simple, explainable solutions.

The assessment explicitly emphasizes good engineering judgment rather than maximum system complexity. Therefore, additional infrastructure should only be introduced when it solves a demonstrated problem.
