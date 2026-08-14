import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { debounceTime, startWith } from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { EmployeeService } from '../../../core/services/employee.service';
import { Employee } from '../../../core/models/employee.model';
@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
  ],
  templateUrl: './employee-list.html',
  styleUrl: './employee-list.scss',
})
export class EmployeeList {
  private service = inject(EmployeeService);
  employees: Employee[] = [];
  search = new FormControl('', { nonNullable: true });
  columns = ['employeeCode', 'name', 'email', 'department', 'designation', 'status', 'actions'];
  constructor() {
    this.load();
    this.search.valueChanges.pipe(startWith(''), debounceTime(250)).subscribe((x) => this.load(x));
  }
  load(q = '') {
    (q ? this.service.search(q) : this.service.getAll()).subscribe(
      (p) => (this.employees = p.content),
    );
  }
  delete(id: number) {
    if (confirm('Delete this employee?'))
      this.service.delete(id).subscribe(() => this.load(this.search.value));
  }
}
