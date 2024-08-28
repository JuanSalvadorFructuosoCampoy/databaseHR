package com.hr.ServiceTest;

import com.hr.entity.Job;
import com.hr.exception.MultipleException;
import com.hr.exception.TrabajoNoEncontradoException;
import com.hr.repository.JobRepository;
import com.hr.service.JobServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class JobServiceImplTest {

    @MockBean
    private JobRepository jobRepository;

    @Mock
    private JobServiceImpl jobServiceImpl;

    @BeforeEach
    void setUp() {
        jobServiceImpl = new JobServiceImpl(jobRepository);
    }

    @Test
    void testListar() {
        Job job = new Job();
        job.setJobId("DV");

        Job job2 = new Job();
        job2.setJobId("MN");

        List<Job> jobs = List.of(job, job2);
        Page<Job> page = new PageImpl<>(jobs);

        when(jobRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Job> result = jobServiceImpl.listar(Pageable.unpaged());
        assertEquals(page.map(Job::getJobId), result.map(Job::getJobId));
    }

    @Test
    void testPorId() throws TrabajoNoEncontradoException {
        Job job = new Job();
        job.setJobId("DV");
        when(jobRepository.findById(anyString())).thenReturn(Optional.of(job));
        Job result = jobServiceImpl.porId("DV").get();
        assertEquals(job.getJobId(), result.getJobId());
    }

    @Test
    void testPorIdException() throws TrabajoNoEncontradoException {
        when(jobRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(TrabajoNoEncontradoException.class, () -> jobServiceImpl.porId("DV"));
    }

    @Test
    void testSaveNuevo() throws MultipleException {
        Job job = new Job();
        job.setJobId("DV");
        job.setJobTitle("Developer");
        job.setMinSalary(1000);
        job.setMaxSalary(2000);
        when(jobRepository.findById(anyString())).thenReturn(Optional.empty());
        when(jobRepository.save(any(Job.class))).thenReturn(job);
        Job result = jobServiceImpl.saveNuevo(job);
        assertEquals(job.getJobId(), result.getJobId());
    }

    @Test
    void testSaveNuevoException() {
        Job job = new Job();
        job.setJobId("");
        job.setJobTitle("");
        job.setMinSalary(0);
        job.setMaxSalary(0);
        when(jobRepository.findById(anyString())).thenReturn(Optional.of(job));
        assertThrows(MultipleException.class, () -> jobServiceImpl.saveNuevo(job));
    }

    @Test
    void testSaveEditar() throws MultipleException {
        Job job = new Job();
        job.setJobId("DV");
        job.setJobTitle("Developer");
        job.setMinSalary(1000);
        job.setMaxSalary(2000);
        when(jobRepository.findById(anyString())).thenReturn(Optional.of(job));
        when(jobRepository.save(any(Job.class))).thenReturn(job);
        Job result = jobServiceImpl.saveEditar(job);
        assertEquals(job.getJobId(), result.getJobId());
    }

    @Test
    void testSaveEditarException() {
        Job job = new Job();
        job.setJobId("");
        job.setJobTitle("");
        job.setMinSalary(0);
        job.setMaxSalary(0);
        when(jobRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(MultipleException.class, () -> jobServiceImpl.saveEditar(job));
    }

    @Test
    void testEliminar() throws TrabajoNoEncontradoException {
        Job job = new Job();
        job.setJobId("DV");
        when(jobRepository.findById(anyString())).thenReturn(Optional.of(job));
        jobServiceImpl.eliminar("DV");
    }

    @Test
    void testEliminarException() {
        when(jobRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(TrabajoNoEncontradoException.class, () -> jobServiceImpl.eliminar("DV"));
    }

}
