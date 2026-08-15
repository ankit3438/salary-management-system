import { Injectable } from '@angular/core';

import {
  HttpClient,
  HttpParams
} from '@angular/common/http';

import { Observable } from 'rxjs';

import { Employee } from '../models/employee.model';
import { PageResponse } from '../models/api-response.model';


@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private apiUrl = '/api/employees';


  constructor(
    private http: HttpClient
  ) {}


  /* =========================
     GET ALL EMPLOYEES
     ========================= */

  getAll(
    page = 0,
    size = 20
  ): Observable<PageResponse<Employee>> {

    return this.http.get<PageResponse<Employee>>(
      this.apiUrl,
      {
        params: new HttpParams()
          .set('page', page)
          .set('size', size)
      }
    );

  }


  /* =========================
     SEARCH / FILTER EMPLOYEES
     ========================= */

  search(
    search?: string,
    department?: string,
    country?: string,
    status?: string,
    page = 0,
    size = 20
  ): Observable<PageResponse<Employee>> {


    let params = new HttpParams()
      .set('page', page)
      .set('size', size);


    /*
     * Search
     */

    if (search?.trim()) {

      params = params.set(
        'search',
        search.trim()
      );

    }


    /*
     * Department
     */

    if (department?.trim()) {

      params = params.set(
        'department',
        department.trim()
      );

    }


    /*
     * Country
     */

    if (country?.trim()) {

      params = params.set(
        'country',
        country.trim()
      );

    }


    /*
     * Status
     */

    if (status?.trim()) {

      params = params.set(
        'status',
        status.trim()
      );

    }


    /*
     * Example generated URL:
     *
     * /api/employees/search
     *     ?search=John
     *     &department=Engineering
     *     &country=India
     *     &status=ACTIVE
     *     &page=0
     *     &size=20
     */

    return this.http.get<PageResponse<Employee>>(
      `${this.apiUrl}/search`,
      {
        params
      }
    );

  }


  /* =========================
     GET EMPLOYEE BY ID
     ========================= */

  getById(
    id: number
  ): Observable<Employee> {

    return this.http.get<Employee>(
      `${this.apiUrl}/${id}`
    );

  }


  /* =========================
     CREATE EMPLOYEE
     ========================= */

  create(
    employee: Omit<Employee, 'id'>
  ): Observable<Employee> {

    return this.http.post<Employee>(
      this.apiUrl,
      employee
    );

  }


  /* =========================
     UPDATE EMPLOYEE
     ========================= */

  update(
    id: number,
    employee: Omit<Employee, 'id'>
  ): Observable<Employee> {

    return this.http.put<Employee>(
      `${this.apiUrl}/${id}`,
      employee
    );

  }


  /* =========================
     DELETE EMPLOYEE
     ========================= */

  delete(
    id: number
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );

  }

}