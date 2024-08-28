package com.hr.ControllerTest;

import com.hr.service.JobHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JobHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobHistoryService jobHistoryService;

    @Test
    void testListar() throws Exception {
        mockMvc.perform(get("/job-history"))
                .andExpect(status().isOk());
    }

    @Test
    void testPorId() throws Exception {
        mockMvc.perform(get("/job-history/10"))
                .andExpect(status().isOk());
    }
}
