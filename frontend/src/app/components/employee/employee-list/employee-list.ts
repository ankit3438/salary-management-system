import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { debounceTime, distinctUntilChanged, combineLatest, startWith, switchMap } from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';

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
    MatSelectModule,
    MatPaginatorModule
  ],
  templateUrl: './employee-list.html',
  styleUrl: './employee-list.scss'
})
export class EmployeeList {
  private service = inject(EmployeeService);

  employees = signal<Employee[]>([]);
  totalElements = 0;
  pageSize = 20;
  pageIndex = 0;

  search = new FormControl('', { nonNullable: true });
  department = new FormControl('', { nonNullable: true });
  country = new FormControl('', { nonNullable: true });
  status = new FormControl('', { nonNullable: true });

  columns = [
    'employeeCode',
    'name',
    'email',
    'department',
    'designation',
    'country',
    'status',
    'joiningDate',
    'actions'
  ];

  departments: string[] = [];
  countries: string[] = [];
  statuses = ['ACTIVE', 'INACTIVE'];

  constructor() {
    this.loadFilters();
    this.applyFilters(); // initial load
  }

  loadFilters(): void {
    this.service.getFilters().subscribe({
      next: (filters) => {
        this.departments = filters.departments ?? [];
        this.countries = filters.countries ?? [];
      },
      error: () => {
        this.departments = [];
        this.countries = [];
      }
    });
  }

  applyFilters(): void {
    const search = this.search.value.trim();
    const department = this.department.value;
    const country = this.country.value;
    const status = this.status.value;

    const hasFilters = search !== '' || department !== '' || country !== '' || status !== '';

    const request$ = hasFilters
      ? this.service.search(search, department, country, status, this.pageIndex, this.pageSize)
      : this.service.getAll(this.pageIndex, this.pageSize);

    request$.subscribe({
      next: (response: any) => {   // <-- treat response as any
        this.employees.set(response.content);
        this.totalElements = response.totalElements;
        this.pageSize = response.size;     // backend field
        this.pageIndex = response.number;  // backend field
      },
      error: (err) => {
        console.error('Failed to load employees:', err);
        this.employees.set([]);
      }
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.applyFilters();
  }

  getInitials(employee: Employee): string {
    const first = employee.firstName?.charAt(0) ?? '';
    const last = employee.lastName?.charAt(0) ?? '';
    return `${first}${last}`.toUpperCase();
  }

  delete(id: number): void {
    if (!confirm('Delete this employee?')) return;
    this.service.delete(id).subscribe({
      next: () => this.applyFilters(),
      error: (err) => console.error('Failed to delete employee:', err)
    });
  }

  clearFilters(): void {
    this.search.setValue('');
    this.department.setValue('');
    this.country.setValue('');
    this.status.setValue('');
    this.pageIndex = 0; // reset to first page
    this.applyFilters();
  }
}
