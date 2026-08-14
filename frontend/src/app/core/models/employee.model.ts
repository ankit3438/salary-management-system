export interface Employee {
  id: number;
  employeeCode: string;
  firstName: string;
  lastName: string;
  email: string;
  department: string;
  designation: string;
  country: string;
  status: 'ACTIVE' | 'INACTIVE';
  dateOfJoining: string;
}
