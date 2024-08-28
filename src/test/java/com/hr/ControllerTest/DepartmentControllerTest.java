package com.hr.ControllerTest;

import com.hr.dto.DepartmentDTO;
import com.hr.dto.DepartmentDTOGet;
import com.hr.entity.Department;
import com.hr.entity.Location;
import com.hr.exception.DepartamentoNoEncontradoException;
import com.hr.exception.MultipleException;
import com.hr.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @MockBean
    private DepartmentDTO departmentDTO;

    @BeforeEach
    void setUp() {
        departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentId(10);
        departmentDTO.setDepartmentName("Administration");
        departmentDTO.setManager("Steven King");
        departmentDTO.setLocationId(1);
    }

    @Test
    void testListar() throws Exception {
        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk());
    }

    @Test
    void testPorId() throws Exception {
        DepartmentDTOGet departmentGet = new DepartmentDTOGet();
        departmentGet.setDepartmentId(10);
        departmentGet.setDepartmentName("Administration");
        departmentGet.setManager("Steven King");
        departmentGet.setLocation(new Location());

        when(departmentService.porId(10)).thenReturn(Optional.ofNullable(departmentGet));
        mockMvc.perform(get("/departments/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departmentId").value(10));
    }

    @Test
    void testSave() throws Exception {
        when(departmentService.save(departmentDTO)).thenReturn(departmentDTO);
        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"departmentId\": 10,\n" +
                                "    \"departmentName\": \"Administration\",\n" +
                                "    \"manager\": \"Steven King\",\n" +
                                "    \"locationId\": 1\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        DepartmentDTOGet department = new DepartmentDTOGet();
        department.setDepartmentId(10);

        DepartmentDTO depart = new DepartmentDTO();
        depart.setDepartmentId(10);

        when(departmentService.save(depart)).thenReturn(depart);
        when(departmentService.porId(10)).thenReturn(Optional.ofNullable(department));
        mockMvc.perform(put("/departments/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"departmentId\": 10,\n" +
                                "    \"departmentName\": \"Administration\",\n" +
                                "    \"manager\": \"Steven King\",\n" +
                                "    \"locationId\": 1\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateException() throws Exception {
        when(departmentService.porId(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(put("/departments/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"departmentId\": 10,\n" +
                                "    \"departmentName\": \"Administration\",\n" +
                                "    \"manager\": \"Steven King\",\n" +
                                "    \"locationId\": 1\n" +
                                "}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminar() throws Exception {
        DepartmentDTOGet depart = new DepartmentDTOGet();
        depart.setDepartmentId(10);

        when(departmentService.porId(10)).thenReturn(Optional.of(depart));
        mockMvc.perform(delete("/departments/10"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarNoEncontrado() throws Exception {
        when(departmentService.porId(10)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/departments/10"))
                .andExpect(status().isNotFound());
    }
}
