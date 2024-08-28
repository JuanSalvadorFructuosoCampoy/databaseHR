package com.hr.ControllerTest;

import com.hr.entity.EmpDetailsView;
import com.hr.service.EmpDetailsViewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmpDetailsViewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpDetailsViewService empDetailsViewService;

    @MockBean
    private EmpDetailsView empDetailsView;

    @BeforeEach
    void setUp() {
        empDetailsView = new EmpDetailsView();
        empDetailsView.setEmployeeId(100);
        empDetailsView.setJobId("AD_VP");
        empDetailsView.setManagerId(100);
        empDetailsView.setDepartmentId(90);
        empDetailsView.setLocationId(1800);
        empDetailsView.setCountryId('U');
        empDetailsView.setFirstName("Steven");
        empDetailsView.setLastName("King");
        empDetailsView.setSalary(24000f);
        empDetailsView.setCommissionPct(null);
        empDetailsView.setDepartmentName("IT");
        empDetailsView.setJobTitle("Administration Vice President");
        empDetailsView.setCity("Seattle");
        empDetailsView.setStateProvince("Washington");
        empDetailsView.setCountryName("United States");
        empDetailsView.setRegionName("Americas");
    }

    @Test
    void testListar() throws Exception {
        mockMvc.perform(get("/empDetailsView"))
                .andExpect(status().isOk());
    }

    @Test
    void testPorId() throws Exception {
        when(empDetailsViewService.porId(100)).thenReturn(Optional.ofNullable(empDetailsView));
        mockMvc.perform(get("/empDetailsView/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(100));
    }

}
