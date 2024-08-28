package com.hr.service;

import com.hr.entity.Job;
import com.hr.exception.*;
import com.hr.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private JobRepository jobRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Job> listar(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Job> porId(String id) throws TrabajoNoEncontradoException {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isEmpty()) {
            throw new TrabajoNoEncontradoException("No existe el trabajo con el ID: " + id);
        }
        return job;
    }

    @Override
    public Job saveNuevo(Job job) throws MultipleException {
        MultipleException multipleException = new MultipleException();
        if (job.getMinSalary() < 0 || job.getMaxSalary() < 0 || job.getMinSalary() >= job.getMaxSalary()) {
            multipleException.addException(new SueldosIncorrectosException("Los salarios están incorrectos"));
        }
        if (job.getJobTitle().trim().isEmpty()) {
            multipleException.addException(new JobTitleNoEncontradoException("El título del trabajo no puede estar vacío"));
        }
        if(job.getJobId().trim().isEmpty()) {
            multipleException.addException(new TrabajoNoEncontradoException("El ID del trabajo no puede estar vacío"));
        }
        if(jobRepository.findById(job.getJobId()).isPresent()) {
            multipleException.addException(new TrabajoDuplicadoException("El trabajo con ID: " + job.getJobId() + " ya existe"));
        }
        if (multipleException.hasExceptions()) {
            throw multipleException;
        } else {
            return jobRepository.save(job);
        }
    }

    @Override
    public Job saveEditar(Job job) throws MultipleException {
        MultipleException multipleException = new MultipleException();
        if (job.getMinSalary() < 0 || job.getMaxSalary() < 0 || job.getMinSalary() >= job.getMaxSalary()) {
            multipleException.addException(new SueldosIncorrectosException("Los salarios están incorrectos"));
        }
        if (job.getJobTitle().trim().isEmpty()) {
            multipleException.addException(new JobTitleNoEncontradoException("El título del trabajo no puede estar vacío"));
        }
        if (jobRepository.findById(job.getJobId()).isEmpty()) {
            multipleException.addException(new TrabajoNoEncontradoException("No existe el trabajo con ID: " + job.getJobId()));
        }
        if (multipleException.hasExceptions()) {
            throw multipleException;
        } else {
            return jobRepository.save(job);
        }
    }

    @Override
    public void eliminar(String id) throws TrabajoNoEncontradoException {
        if (jobRepository.findById(id).isEmpty()) {
            throw new TrabajoNoEncontradoException("No existe el trabajo con ID: " + id);
        } else {
            jobRepository.deleteById(id);
        }
    }

}
