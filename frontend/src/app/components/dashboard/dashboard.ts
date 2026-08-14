import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { AnalyticsService } from '../../core/services/analytics.service';
import { AnalyticsSummary } from '../../core/models/analytics.model';
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard {
  private service = inject(AnalyticsService);
  summary: AnalyticsSummary = {
    totalEmployees: 0,
    activeEmployees: 0,
    totalPayroll: 0,
    averageSalary: 0,
  };
  constructor() {
    this.service.getSummary().subscribe((x) => (this.summary = x));
  }
}
