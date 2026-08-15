import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { AnalyticsService } from '../../core/services/analytics.service';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.scss'],
})
export class Dashboard {
  summary: any = {
    totalEmployees: 0,
    activeEmployees: 0,
    totalPayrollByCurrency: {},
    averageSalaryByCurrency: {},
  };

  constructor(
    private service: AnalyticsService,
    private cdr: ChangeDetectorRef
  ) {
    this.service.getSummary().subscribe((x) => {
      console.log('API response:', x);
      this.summary = x;
      this.cdr.detectChanges(); // ensures UI updates
    });
  }
}
