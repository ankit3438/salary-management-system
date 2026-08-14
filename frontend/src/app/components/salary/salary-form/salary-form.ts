import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { SalaryService } from '../../../core/services/salary.service';
@Component({
  selector: 'app-salary-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
  ],
  templateUrl: './salary-form.html',
  styleUrl: './salary-form.scss',
})
export class SalaryForm {
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private service = inject(SalaryService);
  employeeId = Number(this.route.snapshot.paramMap.get('employeeId'));
  form = this.fb.nonNullable.group({
    baseSalary: [0, [Validators.required, Validators.min(0)]],
    bonus: [0, [Validators.required, Validators.min(0)]],
    currency: ['USD', Validators.required],
    effectiveFrom: ['', Validators.required],
  });
  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.service
      .add(this.employeeId, this.form.getRawValue())
      .subscribe(() => this.router.navigate(['/employees', this.employeeId, 'salary']));
  }
}
