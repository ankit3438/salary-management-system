import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Employee } from '../models/employee.model';
import { PageResponse } from '../models/api-response.model';
@Injectable({ providedIn: 'root' })
export class EmployeeService {
  private employees: Employee[] = [
    {
      id: 1,
      employeeCode: 'EMP001',
      firstName: 'Amit',
      lastName: 'Sharma',
      email: 'amit@example.com',
      department: 'Engineering',
      designation: 'Senior Java Developer',
      country: 'India',
      status: 'ACTIVE',
      dateOfJoining: '2023-01-15',
    },
    {
      id: 2,
      employeeCode: 'EMP002',
      firstName: 'Priya',
      lastName: 'Verma',
      email: 'priya@example.com',
      department: 'Finance',
      designation: 'Financial Analyst',
      country: 'India',
      status: 'ACTIVE',
      dateOfJoining: '2022-06-20',
    },
    {
      id: 3,
      employeeCode: 'EMP003',
      firstName: 'John',
      lastName: 'Smith',
      email: 'john@example.com',
      department: 'Engineering',
      designation: 'Backend Developer',
      country: 'UK',
      status: 'ACTIVE',
      dateOfJoining: '2024-02-10',
    },
  ];
  getAll(page = 0, size = 20): Observable<PageResponse<Employee>> {
    const s = page * size;
    return of({
      content: this.employees.slice(s, s + size),
      totalElements: this.employees.length,
      pageSize: size,
      pageNumber: page,
    });
  }
  search(q: string, page = 0, size = 20): Observable<PageResponse<Employee>> {
    const x = q.toLowerCase().trim();
    const f = this.employees.filter((e) =>
      `${e.firstName} ${e.lastName} ${e.employeeCode} ${e.email} ${e.department}`
        .toLowerCase()
        .includes(x),
    );
    const s = page * size;
    return of({
      content: f.slice(s, s + size),
      totalElements: f.length,
      pageSize: size,
      pageNumber: page,
    });
  }
  getById(id: number): Observable<Employee | undefined> {
    return of(this.employees.find((e) => e.id === id));
  }
  create(e: Omit<Employee, 'id'>): Observable<Employee> {
    const n = { ...e, id: Math.max(...this.employees.map((x) => x.id), 0) + 1 };
    this.employees = [...this.employees, n];
    return of(n);
  }
  update(id: number, e: Omit<Employee, 'id'>): Observable<Employee> {
    const n = { ...e, id };
    this.employees = this.employees.map((x) => (x.id === id ? n : x));
    return of(n);
  }
  delete(id: number): Observable<void> {
    this.employees = this.employees.filter((x) => x.id !== id);
    return of(void 0);
  }
}
