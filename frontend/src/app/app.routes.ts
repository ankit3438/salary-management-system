import { Routes } from '@angular/router';
import { Dashboard } from './components/dashboard/dashboard';
import { EmployeeList } from './components/employee/employee-list/employee-list';
import { EmployeeDetails } from './components/employee/employee-details/employee-details';
import { EmployeeForm } from './components/employee/employee-form/employee-form';
import { SalaryForm } from './components/salary/salary-form/salary-form';
import { SalaryHistory } from './components/salary/salary-history/salary-history';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  { path: 'dashboard', component: Dashboard },
  { path: 'employees', component: EmployeeList },
  { path: 'employees/new', component: EmployeeForm },
  { path: 'employees/:id/edit', component: EmployeeForm },
  { path: 'employees/:id', component: EmployeeDetails },
  { path: 'employees/:employeeId/salary/add', component: SalaryForm },
    { path: 'employees/:employeeId/salary', component: SalaryHistory },
  { path: 'employees/:employeeId/salary/add', component: SalaryForm },
  { path: '**', redirectTo: 'dashboard' },
];
