import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import {
  ActivatedRoute,
  Router,
  RouterLink
} from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { EmployeeService } from '../../../core/services/employee.service';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatSnackBarModule
  ],
  templateUrl: './employee-form.html',
  styleUrl: './employee-form.scss'
})
export class EmployeeForm {

  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private service = inject(EmployeeService);
  private snackBar = inject(MatSnackBar);

  id = Number(this.route.snapshot.paramMap.get('id'));

  isEdit = !!this.id;

  form = this.fb.nonNullable.group({
    employeeCode: ['', Validators.required],

    firstName: ['', Validators.required],

    lastName: ['', Validators.required],

    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],

    department: ['', Validators.required],

    designation: ['', Validators.required],

    country: [
      'India',
      Validators.required
    ],

    status: [
      'ACTIVE' as 'ACTIVE' | 'INACTIVE',
      Validators.required
    ],

    joiningDate: [
      '',
      Validators.required
    ]
  });

  constructor() {

    // Edit employee
    if (this.isEdit) {

      this.service.getById(this.id).subscribe({

        next: (employee) => {

          if (employee) {
            this.form.patchValue(employee);
          }

        },

        error: (error) => {

          console.error(
            'Failed to load employee:',
            error
          );

          this.showError(
            'Failed to load employee'
          );

        }

      });

    }

  }


  submit() {

    // -----------------------------
    // Frontend validation
    // -----------------------------

    if (this.form.invalid) {

      this.form.markAllAsTouched();

      return;
    }


    const value = this.form.getRawValue();


    // -----------------------------
    // EDIT EMPLOYEE
    // -----------------------------

    if (this.isEdit) {

      this.service.update(
        this.id,
        value
      ).subscribe({

        next: () => {

          this.showSuccess(
            'Employee updated successfully'
          );

          // Stay on edit page
          this.form.markAsPristine();

        },

        error: (error) => {

          console.error(
            'Failed to update employee:',
            error
          );

          this.showBackendError(error);

        }

      });

      return;
    }


    // -----------------------------
    // CREATE EMPLOYEE
    // -----------------------------

    this.service.create(value).subscribe({

      next: (employee) => {

        console.log(
          'Employee created:',
          employee
        );

        this.showSuccess(
          'Employee created successfully'
        );

        // IMPORTANT:
        // Do NOT navigate anywhere.
        // Stay on Add Employee page.

        this.form.reset({
          employeeCode: '',
          firstName: '',
          lastName: '',
          email: '',
          department: '',
          designation: '',
          country: 'India',
          status: 'ACTIVE',
          joiningDate: ''
        });

      },

      error: (error) => {

        console.error(
          'Failed to create employee:',
          error
        );

        this.showBackendError(error);

      }

    });

  }


  // ============================================================
  // SUCCESS MESSAGE
  // ============================================================

  private showSuccess(message: string) {

    this.snackBar.open(
      message,
      'Close',
      {
        duration: 4000,

        horizontalPosition: 'right',

        verticalPosition: 'top',

        panelClass: [
          'success-snackbar'
        ]
      }
    );

  }


  // ============================================================
  // ERROR MESSAGE
  // ============================================================

  private showError(message: string) {

    this.snackBar.open(
      message,
      'Close',
      {
        duration: 5000,

        horizontalPosition: 'right',

        verticalPosition: 'top',

        panelClass: [
          'error-snackbar'
        ]
      }
    );

  }


  // ============================================================
  // BACKEND ERROR HANDLING
  // ============================================================

  private showBackendError(error: any) {

    let message =
      'Failed to create employee';


    // Spring Boot usually sends:
    //
    // {
    //   "message": "Employee with email xxx already exists"
    // }
    //

    if (error?.error?.message) {

      message = error.error.message;

    }

    // Sometimes backend returns plain text

    else if (
      typeof error?.error === 'string' &&
      error.error.trim() !== ''
    ) {

      message = error.error;

    }

    // Sometimes HttpErrorResponse has message

    else if (
      error?.message &&
      error.message.trim() !== ''
    ) {

      message = error.message;

    }


    this.showError(message);

  }

}