# AI Usage — Salary Management System

## 1. Purpose

The assessment explicitly asks candidates to use AI tools to accelerate development while maintaining correctness and quality.

AI is being used as an engineering assistant rather than as a replacement for design decisions, testing, or review.

## 2. Requirement Analysis

AI can be used to:
- Break down the assessment into functional requirements.
- Identify ambiguities.
- Brainstorm HR workflows.
- Identify potential edge cases.

Final requirements remain the responsibility of the developer.

## 3. Architecture

AI can be used to:
- Explore architecture alternatives.
- Compare modular monolith and microservice approaches.
- Review API boundaries.
- Identify potential scalability concerns.

Final architectural decisions are documented with explicit reasoning.

## 4. Backend Development

AI can assist with:
- Spring Boot boilerplate.
- DTOs.
- Repository interfaces.
- Validation examples.
- Exception handling.
- SQL query drafts.

Generated code is reviewed before being committed.

## 5. Testing

AI can help identify:
- Missing test cases.
- Boundary conditions.
- Invalid input scenarios.
- Service-layer test scenarios.
- Integration test scenarios.

Tests must be deterministic and independently verified.

## 6. Frontend Development

AI can assist with:
- Angular component boilerplate.
- Form validation.
- Table and dashboard layouts.
- API service skeletons.
- Chart configuration.

UI behavior is verified manually.

## 7. Code Review

AI can be used as an additional review layer for:
- Potential N+1 queries.
- Error handling.
- Null handling.
- API consistency.
- Code duplication.
- Test coverage gaps.
- Security concerns.

AI suggestions are not automatically accepted.

## 8. Example Prompt Categories

### Requirements

> Analyze the provided salary management assessment and identify the minimum viable product, explicit requirements, and reasonable out-of-scope items. Do not invent requirements that are not supported by the assessment.

### Backend Review

> Review this Spring Boot service for correctness, maintainability, transaction boundaries, validation, and potential performance issues. Identify problems and explain why they matter.

### Testing

> Review this service and propose meaningful deterministic unit tests covering happy paths, validation failures, boundary cases, and business rules.

### SQL Review

> Review this query for correctness and performance with approximately 10,000 employee records. Suggest indexes only where justified by the query pattern.

## 9. Human Verification

Every AI-generated contribution should be:
1. Reviewed.
2. Understood by the developer.
3. Compiled/executed.
4. Covered by appropriate tests where applicable.
5. Manually verified before submission.

## 10. Principle

AI accelerates implementation, but the developer remains responsible for:
- Requirements.
- Architecture.
- Correctness.
- Security.
- Testing.
- Performance.
- Final code quality.
