import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AnalyticsSummary } from '../models/analytics.model';

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService {
  private apiUrl = '/api/analytics'; // base path from your Spring Boot controller

  constructor(private http: HttpClient) {}

  getSummary(): Observable<AnalyticsSummary> {
    return this.http.get<AnalyticsSummary>(`${this.apiUrl}/summary`);
  }

  getSalaryByDepartment(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/by-department`);
  }

  getSalaryByCountry(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/by-country`);
  }
}
