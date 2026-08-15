package com.backend.Controller;

import com.backend.Controllers.EmployeeController;
import com.backend.Services.EmployeeService;
import com.backend.dto.EmployeeFilterResponse;
import com.backend.dto.EmployeeRequest;
import com.backend.dto.EmployeeResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;


    // ============================================================
    // GET /api/employees
    // ============================================================

    @Test
    void getAllEmployees_shouldReturn200() throws Exception {

        EmployeeResponse employee = new EmployeeResponse();

        Page<EmployeeResponse> page =
                new PageImpl<>(List.of(employee));

        when(employeeService.getAllEmployees(any()))
                .thenReturn(page);

        mockMvc.perform(
                get("/api/employees")
                        .param("page", "0")
                        .param("size", "20")
        )
        .andExpect(status().isOk())
        .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(employeeService, times(1))
                .getAllEmployees(any());
    }


    // ============================================================
    // GET /api/employees/search
    // ============================================================

    @Test
    void searchEmployees_shouldReturn200() throws Exception {

        EmployeeResponse employee = new EmployeeResponse();

        Page<EmployeeResponse> page =
                new PageImpl<>(List.of(employee));

        when(employeeService.searchEmployees(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                any()
        )).thenReturn(page);

        mockMvc.perform(
                get("/api/employees/search")
                        .param("search", "Ankit")
                        .param("department", "IT")
                        .param("country", "India")
                        .param("status", "ACTIVE")
                        .param("page", "0")
                        .param("size", "20")
        )
        .andExpect(status().isOk())
        .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(employeeService, times(1))
                .searchEmployees(
                        eq("Ankit"),
                        eq("IT"),
                        eq("India"),
                        eq("ACTIVE"),
                        any()
                );
    }


    // ============================================================
    // GET /api/employees/filters
    // ============================================================

    @Test
    void getEmployeeFilters_shouldReturn200() throws Exception {

        EmployeeFilterResponse response =
                new EmployeeFilterResponse();

        when(employeeService.getEmployeeFilters())
                .thenReturn(response);

        mockMvc.perform(
                get("/api/employees/filters")
        )
        .andExpect(status().isOk())
        .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(employeeService, times(1))
                .getEmployeeFilters();
    }


    // ============================================================
    // GET /api/employees/{id}
    // ============================================================

    @Test
    void getEmployeeById_shouldReturn200() throws Exception {

        EmployeeResponse response =
                new EmployeeResponse();

        when(employeeService.getEmployeeById(1L))
                .thenReturn(response);

        mockMvc.perform(
                get("/api/employees/1")
        )
        .andExpect(status().isOk())
        .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(employeeService, times(1))
                .getEmployeeById(1L);
    }


    // ============================================================
    // GET /api/employees/code/{employeeCode}
    // ============================================================

    @Test
    void getEmployeeByCode_shouldReturn200() throws Exception {

        EmployeeResponse response =
                new EmployeeResponse();

        when(employeeService.getEmployeeByCode("EMP001"))
                .thenReturn(response);

        mockMvc.perform(
                get("/api/employees/code/EMP001")
        )
        .andExpect(status().isOk())
        .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(employeeService, times(1))
                .getEmployeeByCode("EMP001");
    }


    // ============================================================
    // POST /api/employees
    // ============================================================

    @Test
    void createEmployee_shouldReturn201() throws Exception {

        EmployeeRequest request =
                new EmployeeRequest();

        EmployeeResponse response =
                new EmployeeResponse();

        when(employeeService.createEmployee(any(EmployeeRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "employeeCode": "EMP001",
                                    "firstName": "Ankit",
                                    "lastName": "Jha",
                                    "email": "ankit@example.com"
                                }
                                """)
        )
        .andExpect(status().isCreated())
        .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(employeeService, times(1))
                .createEmployee(any(EmployeeRequest.class));
    }


    // ============================================================
    // PUT /api/employees/{id}
    // ============================================================

    @Test
    void updateEmployee_shouldReturn200() throws Exception {

        EmployeeResponse response =
                new EmployeeResponse();

        when(employeeService.updateEmployee(
                eq(1L),
                any(EmployeeRequest.class)
        )).thenReturn(response);

        mockMvc.perform(
                put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "employeeCode": "EMP001",
                                    "firstName": "Ankit",
                                    "lastName": "Jha",
                                    "email": "ankit@example.com"
                                }
                                """)
        )
        .andExpect(status().isOk())
        .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(employeeService, times(1))
                .updateEmployee(
                        eq(1L),
                        any(EmployeeRequest.class)
                );
    }


    // ============================================================
    // DELETE /api/employees/{id}
    // ============================================================

    @Test
    void deleteEmployee_shouldReturn204() throws Exception {

        doNothing()
                .when(employeeService)
                .deleteEmployee(1L);

        mockMvc.perform(
                delete("/api/employees/1")
        )
        .andExpect(status().isNoContent());

        verify(employeeService, times(1))
                .deleteEmployee(1L);
    }
}