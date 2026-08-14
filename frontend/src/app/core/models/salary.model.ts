export interface Salary {
  id: number;
  employeeId: number;
  baseSalary: number;
  bonus: number;
  currency: string;
  effectiveFrom: string;
}
export interface SalaryRequest {
  baseSalary: number;
  bonus: number;
  currency: string;
  effectiveFrom: string;
}
