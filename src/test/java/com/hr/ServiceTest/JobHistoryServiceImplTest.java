package com.hr.ServiceTest;

import com.hr.dto.JobHistoryDTO;
import com.hr.entity.Department;
import com.hr.entity.Employee;
import com.hr.entity.Job;
import com.hr.entity.JobHistory;
import com.hr.repository.JobHistoryRepository;
import com.hr.service.JobHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class JobHistoryServiceImplTest {

    @MockBean
    private JobHistoryRepository jobHistoryRepository;

    @Mock
    private JobHistoryServiceImpl jobHistoryService;

    @MockBean
    @Qualifier("jobHistoryModelMapper")
    private ModelMapper jobHistoryModelMapper;

    @BeforeEach
    void setUp() {
        jobHistoryService = new JobHistoryServiceImpl(jobHistoryRepository, jobHistoryModelMapper);
    }

    @Test
    void testListar() {
        JobHistory jobHistory = new JobHistory();
        jobHistory.setEmployee(new Employee());

        JobHistory jobHistory1 = new JobHistory();
        jobHistory1.setEmployee(new Employee());

        List<JobHistory> jobHistoryList = List.of(jobHistory, jobHistory1);
        Page<JobHistory> jobHistoryPage = new PageImpl<>(jobHistoryList);

        when(jobHistoryRepository.findAll(any(Pageable.class))).thenReturn(jobHistoryPage);
        Page<JobHistoryDTO> result = jobHistoryService.listar(Pageable.unpaged());
        assertEquals(jobHistoryPage.map(jobH -> jobHistoryModelMapper.map(jobH, JobHistoryDTO.class)),result);
    }

    @Test
    void testListarPorId() {
        // 1. Crear algunos objetos JobHistory y agregarlos a una lista
        JobHistory jobHistory1 = new JobHistory();
        jobHistory1.setEmployee(new Employee());

        JobHistory jobHistory2 = new JobHistory();
        jobHistory2.setEmployee(new Employee());

        List<JobHistory> jobHistoryList = Arrays.asList(jobHistory1, jobHistory2);

        // 2. Crear una Page de JobHistory a partir de esa lista
        Page<JobHistory> jobHistoryPage = new PageImpl<>(jobHistoryList);

        // 3. Configurar el comportamiento de mock para jobHistoryRepository.findAll() para que devuelva nuestra Page de JobHistory
        when(jobHistoryRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(jobHistoryPage);

        // 4. Llamar al método listarPorId() y obtener el resultado
        Page<JobHistoryDTO> result = jobHistoryService.listarPorId(1, Pageable.unpaged());

        // 5. Verificar que el resultado contiene los objetos JobHistoryDTO correctos
        assertEquals(jobHistoryPage.map(jobHistory -> jobHistoryModelMapper.map(jobHistory, JobHistoryDTO.class)), result);
    }

    @Test
    void testSave() throws ParseException, ParseException {
        JobHistory jobHistory = new JobHistory();
        jobHistory.setEmployee(new Employee());

        // Crear un objeto SimpleDateFormat y usarlo para parsear la cadena
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = sdf.parse("2021-01-01");
        jobHistory.setStartDate(startDate);

        jobHistory.setJobId(new Job());

        Date endDate = sdf.parse("2021-12-31");
        jobHistory.setEndDate(endDate);
        jobHistory.setDepartmentId(new Department());
        jobHistoryService.save(jobHistory);
    }
}
