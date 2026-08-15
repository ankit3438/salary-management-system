package com.backend.Controller;

import com.backend.Controllers.AnalyticsController;
import com.backend.Services.AnalyticsService;
import com.backend.dto.CountrySalaryResponse;
import com.backend.dto.DashboardSummaryResponse;
import com.backend.dto.DepartmentSalaryResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnalyticsController.class)
class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalyticsService analyticsService;


    // ============================================================
    // GET /api/analytics/summary
    // ============================================================

    @Test
    void getDashboardSummary_shouldReturn200() throws Exception {

        DashboardSummaryResponse response =
                new DashboardSummaryResponse();

        when(analyticsService.getDashboardSummary())
                .thenReturn(response);

        mockMvc.perform(
                get("/api/analytics/summary")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }


    // ============================================================
    // GET /api/analytics/by-department
    // ============================================================

    @Test
    void getSalaryByDepartment_shouldReturn200() throws Exception {

        List<DepartmentSalaryResponse> response = List.of(
                new DepartmentSalaryResponse()
        );

        when(analyticsService.getSalaryByDepartment())
                .thenReturn(response);

        mockMvc.perform(
                get("/api/analytics/by-department")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }


    // ============================================================
    // GET /api/analytics/by-country
    // ============================================================

    @Test
    void getSalaryByCountry_shouldReturn200() throws Exception {

        List<CountrySalaryResponse> response = List.of(
                new CountrySalaryResponse()
        );

        when(analyticsService.getSalaryByCountry())
                .thenReturn(response);

        mockMvc.perform(
                get("/api/analytics/by-country")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}