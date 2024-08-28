package com.hr.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr.controller.EmployeeController;
import com.hr.dto.EmployeeDTO;
import com.hr.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //MockBean se usa para simular un objeto que se inyecta en el contexto de Spring. Si ponemos simplemente
    // @Mock, no se inyecta en el contexto de Spring. Por lo que al ejecutar los métodos de EmployeeController,
    // intentará ejecutar los métodos de EmployeeService, que no están implementados desde aquí, y dará error.
    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployeeDTO employeeDTO;

    //Establecemos un objeto de tipo EmployeeDTO para que se envíe en las peticiones POST y PUT
    @BeforeEach
    void setUp() {
        employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(100);
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("john.doe@hr.com");
        employeeDTO.setPhoneNumber("123456789");
        employeeDTO.setHireDate("2021-01-01");
        employeeDTO.setJobName("Developer");
        employeeDTO.setSalary(1000);
        employeeDTO.setCommissionPct(null);
        employeeDTO.setManager("Steven King");
        employeeDTO.setDepartment("IT");
    }

    @Test
    void testListar() throws Exception {
        mockMvc.perform(get("/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testPorId() throws Exception {
        when(employeeService.porId(100)).thenReturn(Optional.of(employeeDTO));
        mockMvc.perform(get("/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testPorIdNoEncontrado() throws Exception {
        when(employeeService.porId(10)).thenReturn(Optional.empty());
        mockMvc.perform(get("/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrear() throws Exception {
        // Convertir employeeDTO a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeDTOJson = objectMapper.writeValueAsString(employeeDTO);

        when(employeeService.save(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(employeeDTOJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testEditar() throws Exception {
        // Convertir employeeDTO a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeDTOJson = objectMapper.writeValueAsString(employeeDTO);

        when(employeeService.porId(100)).thenReturn(Optional.of(employeeDTO));
        when(employeeService.save(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(put("/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testEditarEmpleadoNoExistente() throws Exception {
        when(employeeService.porId(2)).thenReturn(Optional.empty());
        // Realizar una solicitud PUT al endpoint /empleados/{id} con un ID inexistente
        mockMvc.perform(put("/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminar() throws Exception {
        when(employeeService.porId(100)).thenReturn(Optional.of(employeeDTO));

        mockMvc.perform(delete("/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarNoEncontrado() throws Exception {
        when(employeeService.porId(10)).thenReturn(Optional.empty());
        mockMvc.perform(get("/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
