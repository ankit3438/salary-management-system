import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Salary, SalaryRequest } from '../models/salary.model';

@Injectable({ providedIn: 'root' })
export class SalaryService {
  private apiUrl = '/api/employees';

  constructor(private http: HttpClient) {}

  getByEmployee(employeeId: number): Observable<Salary[]> {
    return this.http.get<Salary[]>(`${this.apiUrl}/${employeeId}/salary/history`);
  }

  getCurrent(employeeId: number): Observable<Salary> {
    return this.http.get<Salary>(`${this.apiUrl}/${employeeId}/salary`);
  }

  add(employeeId: number, request: SalaryRequest): Observable<Salary> {
    return this.http.post<Salary>(`${this.apiUrl}/${employeeId}/salary`, request);
  }
}
