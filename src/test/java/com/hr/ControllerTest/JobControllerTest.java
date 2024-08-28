package com.hr.ControllerTest;

import com.hr.entity.Job;
import com.hr.exception.MultipleException;
import com.hr.service.JobService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    @Test
    void testListar() throws Exception {
        mockMvc.perform(get("/jobs"))
                .andExpect(status().isOk());
    }

    @Test
    void testPorId() throws Exception {
        Job trabajo = new Job();
        trabajo.setJobId("AD_VP");
        trabajo.setJobTitle("Administration Vice President");
        trabajo.setMinSalary(15000f);
        trabajo.setMaxSalary(30000f);

        when(jobService.porId("AD_VP")).thenReturn(Optional.of(trabajo));
        mockMvc.perform(get("/jobs/AD_VP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobId").value("AD_VP"));
    }

    @Test
    void testSave() throws Exception {
        Job job = new Job();
        job.setJobId("AD_VP");
        job.setJobTitle("Administration Vice President");
        job.setMinSalary(15000f);
        job.setMaxSalary(30000f);
        when(jobService.saveNuevo(any(Job.class))).thenReturn(job);
        mockMvc.perform(post("/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"jobId\": \"AD_VP\",\n" +
                        "    \"jobTitle\": \"Administration Vice President\",\n" +
                        "    \"minSalary\": 15000,\n" +
                        "    \"maxSalary\": 30000\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.jobId").value("AD_VP"));
    }

    @Test
    void testUpdate() throws Exception {
        Job job = new Job();
        job.setJobId("AD_VP");
        job.setJobTitle("Administration Vice President");
        job.setMinSalary(15000f);
        job.setMaxSalary(30000f);
        when(jobService.saveEditar(any(Job.class))).thenReturn(job);
        when(jobService.porId("AD_VP")).thenReturn(Optional.of(job));
        mockMvc.perform(put("/jobs/AD_VP")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"jobId\": \"AD_VP\",\n" +
                        "    \"jobTitle\": \"Administration Vice President\",\n" +
                        "    \"minSalary\": 15000,\n" +
                        "    \"maxSalary\": 30000\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobId").value("AD_VP"));
    }

    @Test
    void testUpdateException() throws Exception {
        Job job = new Job();
        job.setJobId("AD_VP");
        job.setJobTitle("Administration Vice President");
        job.setMinSalary(15000f);
        job.setMaxSalary(30000f);
        when(jobService.porId(anyString())).thenReturn(Optional.empty());
        when(jobService.saveEditar(any(Job.class))).thenThrow(MultipleException.class);
        when(jobService.porId("AD_VP")).thenReturn(Optional.empty());
        mockMvc.perform(put("/jobs/AD_VP")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"jobId\": \"AD_VP\",\n" +
                        "    \"jobTitle\": \"Administration Vice President\",\n" +
                        "    \"minSalary\": 15000,\n" +
                        "    \"maxSalary\": 30000\n" +
                        "}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminar() throws Exception {
        Job job = new Job();
        job.setJobId("AD_VP");
        job.setJobTitle("Administration Vice President");
        job.setMinSalary(15000f);
        job.setMaxSalary(30000f);
        when(jobService.porId("AD_VP")).thenReturn(Optional.of(job));

        mockMvc.perform(delete("/jobs/AD_VP"))
                .andExpect(status().isNoContent());

        verify(jobService).eliminar("AD_VP");
    }
}
