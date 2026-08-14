import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Salary, SalaryRequest } from '../models/salary.model';
@Injectable({ providedIn: 'root' })
export class SalaryService {
  private salaries: Salary[] = [
    {
      id: 1,
      employeeId: 1,
      baseSalary: 85000,
      bonus: 5000,
      currency: 'USD',
      effectiveFrom: '2025-01-01',
    },
    {
      id: 2,
      employeeId: 2,
      baseSalary: 65000,
      bonus: 3000,
      currency: 'USD',
      effectiveFrom: '2025-01-01',
    },
  ];
  getByEmployee(id: number): Observable<Salary[]> {
    return of(this.salaries.filter((s) => s.employeeId === id));
  }
  add(employeeId: number, r: SalaryRequest): Observable<Salary> {
    const n = { ...r, employeeId, id: Math.max(...this.salaries.map((x) => x.id), 0) + 1 };
    this.salaries = [...this.salaries, n];
    return of(n);
  }
}
