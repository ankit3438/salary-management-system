import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { SalaryService } from '../../../core/services/salary.service';
import { Salary } from '../../../core/models/salary.model';

@Component({
  selector: 'app-salary-history',
  standalone: true,
  imports: [CurrencyPipe, DatePipe, RouterLink, MatButtonModule, MatTableModule],
  templateUrl: './salary-history.html',
  styleUrl: './salary-history.scss',
})
export class SalaryHistory implements OnInit {
  private route = inject(ActivatedRoute);
  private service = inject(SalaryService);
  private cdr = inject(ChangeDetectorRef);

  employeeId!: number;
  salaries: Salary[] = [];
  columns = ['effectiveFrom', 'baseSalary', 'bonus', 'currency'];

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.employeeId = Number(params.get('employeeId'));
      this.service.getByEmployee(this.employeeId).subscribe(x => {
        console.log('Salary history response:', x);
        this.salaries = x;
        this.cdr.detectChanges(); // ✅ force Angular to refresh the view
      });
    });
  }
}
