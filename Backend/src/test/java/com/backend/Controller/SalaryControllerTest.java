package com.backend.Controller;

import com.backend.Controllers.SalaryController;
import com.backend.Services.SalaryService;
import com.backend.dto.SalaryRequest;
import com.backend.dto.SalaryResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SalaryController.class)
class SalaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaryService salaryService;


    // ============================================================
    // POST /api/employees/{employeeId}/salary
    // ============================================================

    @Test
void addSalary_shouldReturn201() throws Exception {

    SalaryResponse response = new SalaryResponse();

    when(salaryService.addSalary(
            eq(1L),
            any(SalaryRequest.class)
    )).thenReturn(response);

    mockMvc.perform(
            post("/api/employees/1/salary")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                                "baseSalary": 75000,
                                "bonus": 10000,
                                "currency": "INR",
                                "effectiveFrom": "2026-08-15"
                            }
                            """)
    )
    .andExpect(status().isCreated())
    .andExpect(
            content().contentTypeCompatibleWith(
                    MediaType.APPLICATION_JSON
            )
    );

    verify(salaryService, times(1))
            .addSalary(
                    eq(1L),
                    any(SalaryRequest.class)
            );
}


    // ============================================================
    // GET /api/employees/{employeeId}/salary
    // ============================================================

    @Test
    void getCurrentSalary_shouldReturn200() throws Exception {

        SalaryResponse response = new SalaryResponse();

        when(salaryService.getCurrentSalary(1L))
                .thenReturn(response);

        mockMvc.perform(
                get("/api/employees/1/salary")
        )
        .andExpect(status().isOk())
        .andExpect(
                content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                )
        );

        verify(salaryService, times(1))
                .getCurrentSalary(1L);
    }


    // ============================================================
    // GET /api/employees/{employeeId}/salary/history
    // ============================================================

    @Test
    void getSalaryHistory_shouldReturn200() throws Exception {

        SalaryResponse salary1 = new SalaryResponse();
        SalaryResponse salary2 = new SalaryResponse();

        List<SalaryResponse> response =
                List.of(salary1, salary2);

        when(salaryService.getSalaryHistory(1L))
                .thenReturn(response);

        mockMvc.perform(
                get("/api/employees/1/salary/history")
        )
        .andExpect(status().isOk())
        .andExpect(
                content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                )
        );

        verify(salaryService, times(1))
                .getSalaryHistory(1L);
    }


    // ============================================================
    // GET /api/employees/{employeeId}/salary/history/paginated
    // ============================================================

    @Test
    void getSalaryHistoryPaginated_shouldReturn200() throws Exception {

        SalaryResponse salary1 = new SalaryResponse();
        SalaryResponse salary2 = new SalaryResponse();

        Page<SalaryResponse> response =
                new PageImpl<>(
                        List.of(salary1, salary2)
                );

        when(salaryService.getSalaryHistoryPaginated(
                eq(1L),
                any()
        )).thenReturn(response);

        mockMvc.perform(
                get("/api/employees/1/salary/history/paginated")
                        .param("page", "0")
                        .param("size", "10")
        )
        .andExpect(status().isOk())
        .andExpect(
                content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                )
        );

        verify(salaryService, times(1))
                .getSalaryHistoryPaginated(
                        eq(1L),
                        any()
                );
    }


    // ============================================================
    // GET /api/employees/{employeeId}/salary/{salaryId}
    // ============================================================

    @Test
    void getSalaryById_shouldReturn200() throws Exception {

        SalaryResponse response = new SalaryResponse();

        when(salaryService.getSalaryById(10L))
                .thenReturn(response);

        mockMvc.perform(
                get("/api/employees/1/salary/10")
        )
        .andExpect(status().isOk())
        .andExpect(
                content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                )
        );

        verify(salaryService, times(1))
                .getSalaryById(10L);
    }
}