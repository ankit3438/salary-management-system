import {
  Component,
  inject,
  signal
} from '@angular/core';

import { CommonModule } from '@angular/common';

import {
  FormControl,
  ReactiveFormsModule
} from '@angular/forms';

import { RouterLink } from '@angular/router';

import {
  debounceTime,
  distinctUntilChanged,
  combineLatest,
  startWith,
  switchMap
} from 'rxjs';

import { MatButtonModule } from '@angular/material/button';

import {
  MatFormFieldModule
} from '@angular/material/form-field';

import {
  MatInputModule
} from '@angular/material/input';

import {
  MatTableModule
} from '@angular/material/table';

import {
  MatSelectModule
} from '@angular/material/select';

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


  // =========================
  // EMPLOYEES
  // =========================

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

  /*
   * These will now come from
   * the backend.
   */

  departments: string[] = [];

  countries: string[] = [];


  /*
   * Status is fixed application
   * data, so we can keep it here.
   */

  statuses = [
    'ACTIVE',
    'INACTIVE'
  ];


  // =========================
  // CONSTRUCTOR
  // =========================

  constructor() {

    /*
     * Load departments and countries
     * from backend when component
     * is opened.
     */

    this.loadFilters();


    // =========================
    // SEARCH INPUT
    // =========================

    const search$ =
      this.search.valueChanges.pipe(
        startWith(''),
        debounceTime(300),
        distinctUntilChanged()
      );


    // =========================
    // DEPARTMENT FILTER
    // =========================

    const department$ =
      this.department.valueChanges.pipe(
        startWith('')
      );


    // =========================
    // COUNTRY FILTER
    // =========================

    const country$ =
      this.country.valueChanges.pipe(
        startWith('')
      );


    // =========================
    // STATUS FILTER
    // =========================

    const status$ =
      this.status.valueChanges.pipe(
        startWith('')
      );


    // =========================
    // COMBINE FILTERS
    // =========================

    combineLatest([
      search$,
      department$,
      country$,
      status$
    ])
      .pipe(

        switchMap(
          ([
            search,
            department,
            country,
            status
          ]) => {

            /*
             * Check whether any filter
             * has been selected.
             */

            const hasFilters =
              search.trim() !== '' ||
              department !== '' ||
              country !== '' ||
              status !== '';


            /*
             * No filters selected
             *
             * GET /api/employees
             */

            if (!hasFilters) {

              return this.service.getAll();

            }


            /*
             * At least one filter selected
             *
             * GET /api/employees/search
             */

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
  // LOAD FILTERS
  // =========================

  loadFilters(): void {

    this.service.getFilters()
      .subscribe({

        next: (filters) => {

          console.log(
            'Employee filters:',
            filters
          );


          /*
           * Values coming from backend
           */

          this.departments =
            filters.departments ?? [];


          this.countries =
            filters.countries ?? [];

        },


        error: (error) => {

          console.error(
            'Failed to load employee filters:',
            error
          );


          /*
           * If API fails, keep dropdowns
           * empty rather than using
           * hardcoded values.
           */

          this.departments = [];

          this.countries = [];

        }

      });

  }


  // =========================
  // GET EMPLOYEE INITIALS
  // =========================

  getInitials(
    employee: Employee
  ): string {

    const first =
      employee.firstName?.charAt(0) ?? '';


    const last =
      employee.lastName?.charAt(0) ?? '';


    return `${first}${last}`.toUpperCase();

  }


  // =========================
  // DELETE EMPLOYEE
  // =========================

  delete(
    id: number
  ): void {

    if (!confirm('Delete this employee?')) {

      return;

    }


    this.service.delete(id)
      .subscribe({

        next: () => {

          /*
           * Reload employees using
           * currently selected filters.
           */

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

  applyFilters(): void {

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


    /*
     * If no filter:
     * GET all employees
     *
     * Otherwise:
     * Search API
     */

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

  clearFilters(): void {

    this.search.setValue('');

    this.department.setValue('');

    this.country.setValue('');

    this.status.setValue('');

  }

}