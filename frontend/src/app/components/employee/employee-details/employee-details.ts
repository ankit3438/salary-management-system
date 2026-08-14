import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { EmployeeService } from '../../../core/services/employee.service';
@Component({
  selector: 'app-employee-details',
  standalone: true,
  imports: [CommonModule, RouterLink, MatButtonModule, MatCardModule],
  templateUrl: './employee-details.html',
  styleUrl: './employee-details.scss',
})
export class EmployeeDetails {
  private route = inject(ActivatedRoute);
  private service = inject(EmployeeService);
  employee: any;
  constructor() {
    this.service
      .getById(Number(this.route.snapshot.paramMap.get('id')))
      .subscribe((e) => (this.employee = e));
  }
}
