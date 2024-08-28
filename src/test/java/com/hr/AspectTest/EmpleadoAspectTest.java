package com.hr.AspectTest;

import com.hr.aspect.EmpleadoAspect;
import com.hr.dto.EmployeeDTO;
import com.hr.entity.*;
import com.hr.repository.EmployeeRepository;
import com.hr.service.JobHistoryService;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

class EmpleadoAspectTest {

    @Mock
    private JobHistoryService jobHistoryService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testActualizarPuesto() throws ParseException {
        // Crear un EmployeeDTO y un Employee para usar en el test
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(1);
        employeeDTO.setJobName("NewJobName");

        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setHireDate(new Date());
        Job job = new Job();
        job.setJobTitle("OldJobName");
        employee.setJob(job);

        // Configurar los mocks
        when(employeeRepository.findById(employeeDTO.getEmployeeId())).thenReturn(Optional.of(employee));
        when(employeeRepository.findByJobTitle(employeeDTO.getJobName())).thenReturn(new Job());

        // Crear el aspect y establecer los mocks
        EmpleadoAspect empleadoAspect = new EmpleadoAspect(jobHistoryService, employeeRepository);

        // Crear un JoinPoint simulado
        JoinPoint joinPoint = mock(JoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[]{employeeDTO});

        // Llamar al método actualizarPuesto
        empleadoAspect.actualizarPuesto(joinPoint);

        // Verificar que los métodos correctos se llaman en los mocks
        verify(jobHistoryService, times(2)).save(any(JobHistory.class));
    }
}