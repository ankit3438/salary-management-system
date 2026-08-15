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
    MatSelectModule
  ],

  templateUrl: './employee-form.html',
  styleUrl: './employee-form.scss'
})
export class EmployeeForm {

  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private service = inject(EmployeeService);

  id = Number(
    this.route.snapshot.paramMap.get('id')
  );

  isEdit = !!this.id;

  form = this.fb.nonNullable.group({

    employeeCode: [
      '',
      Validators.required
    ],

    firstName: [
      '',
      Validators.required
    ],

    lastName: [
      '',
      Validators.required
    ],

    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],

    department: [
      '',
      Validators.required
    ],

    designation: [
      '',
      Validators.required
    ],

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

    if (this.isEdit) {

      this.service
        .getById(this.id)
        .subscribe((employee) => {

          if (employee) {
            this.form.patchValue(employee);
          }

        });
    }
  }

  submit() {

    if (this.form.invalid) {

      this.form.markAllAsTouched();

      return;
    }

    const value =
      this.form.getRawValue();

    const request$ = this.isEdit

      ? this.service.update(
          this.id,
          value
        )

      : this.service.create(
          value
        );

    request$.subscribe((employee) => {

      this.router.navigate([
        '/employees',
        employee.id
      ]);

    });
  }
}