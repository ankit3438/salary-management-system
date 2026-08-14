import { Component, inject } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { SalaryService } from '../../../core/services/salary.service';
import { Salary } from '../../../core/models/salary.model';
@Component({
  selector: 'app-salary-history',
  standalone: true,
  imports: [CurrencyPipe, RouterLink, MatButtonModule, MatTableModule],
  templateUrl: './salary-history.html',
  styleUrl: './salary-history.scss',
})
export class SalaryHistory {
  private route = inject(ActivatedRoute);
  private service = inject(SalaryService);
  employeeId = Number(this.route.snapshot.paramMap.get('employeeId'));
  salaries: Salary[] = [];
  columns = ['effectiveFrom', 'baseSalary', 'bonus', 'currency'];
  constructor() {
    this.service.getByEmployee(this.employeeId).subscribe((x) => (this.salaries = x));
  }
}
