import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import {
  debounceTime,
  distinctUntilChanged,
  combineLatest,
  startWith,
  switchMap
} from 'rxjs';

import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';

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
    MatSelectModule
  ],

  templateUrl: './employee-list.html',
  styleUrl: './employee-list.scss'
})
export class EmployeeList {

  private service = inject(EmployeeService);

  employees = signal<Employee[]>([]);

  // =========================
  // FILTER CONTROLS
  // =========================

  search = new FormControl('', {
    nonNullable: true
  });

  department = new FormControl('', {
    nonNullable: true
  });

  country = new FormControl('', {
    nonNullable: true
  });

  status = new FormControl('', {
    nonNullable: true
  });

  // =========================
  // TABLE COLUMNS
  // =========================

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

  // =========================
  // FILTER OPTIONS
  // =========================

  departments = [
    'IT',
    'HR',
    'Finance',
    'Operations',
    'Engineering'
  ];

  countries = [
    'India',
    'USA',
    'UK',
    'Germany'
  ];

  statuses = [
    'ACTIVE',
    'INACTIVE'
  ];

  constructor() {

    // Search input
    const search$ = this.search.valueChanges.pipe(
      startWith(''),
      debounceTime(300),
      distinctUntilChanged()
    );

    // Department filter
    const department$ = this.department.valueChanges.pipe(
      startWith('')
    );

    // Country filter
    const country$ = this.country.valueChanges.pipe(
      startWith('')
    );

    // Status filter
    const status$ = this.status.valueChanges.pipe(
      startWith('')
    );

    // Combine all filters
    combineLatest([
      search$,
      department$,
      country$,
      status$
    ])
      .pipe(
        switchMap(
          ([search, department, country, status]) => {

            const hasFilters =
              search.trim() !== '' ||
              department !== '' ||
              country !== '' ||
              status !== '';

            /*
             * IMPORTANT:
             *
             * No filter selected:
             * GET /api/employees
             *
             * Any filter selected:
             * GET /api/employees/search
             */

            if (!hasFilters) {
              return this.service.getAll();
            }

            return this.service.search(
              search,
              department,
              country,
              status
            );
          }
        )
      )
      .subscribe({
        next: (response) => {

          console.log(
            'Employees:',
            response.content
          );

          this.employees.set(
            response.content
          );
        },

        error: (error) => {

          console.error(
            'Failed to load employees:',
            error
          );

          this.employees.set([]);
        }
      });
  }

  // =========================
  // GET EMPLOYEE INITIALS
  // =========================

  getInitials(employee: Employee): string {

    const first =
      employee.firstName?.charAt(0) ?? '';

    const last =
      employee.lastName?.charAt(0) ?? '';

    return `${first}${last}`.toUpperCase();
  }

  // =========================
  // DELETE EMPLOYEE
  // =========================

  delete(id: number) {

    if (!confirm('Delete this employee?')) {
      return;
    }

    this.service.delete(id).subscribe({

      next: () => {
        this.applyFilters();
      },

      error: (error) => {

        console.error(
          'Failed to delete employee:',
          error
        );
      }
    });
  }

  // =========================
  // APPLY CURRENT FILTERS
  // =========================

  applyFilters() {

    const search =
      this.search.value.trim();

    const department =
      this.department.value;

    const country =
      this.country.value;

    const status =
      this.status.value;

    const hasFilters =
      search !== '' ||
      department !== '' ||
      country !== '' ||
      status !== '';

    const request$ = hasFilters

      ? this.service.search(
          search,
          department,
          country,
          status
        )

      : this.service.getAll();

    request$.subscribe({

      next: (response) => {

        this.employees.set(
          response.content
        );
      },

      error: (error) => {

        console.error(
          'Failed to load employees:',
          error
        );

        this.employees.set([]);
      }
    });
  }

  // =========================
  // CLEAR FILTERS
  // =========================

  clearFilters() {

    this.search.setValue('');
    this.department.setValue('');
    this.country.setValue('');
    this.status.setValue('');
  }
}