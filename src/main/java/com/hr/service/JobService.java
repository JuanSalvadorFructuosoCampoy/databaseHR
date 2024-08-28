package com.hr.service;

import com.hr.entity.Job;
import com.hr.exception.MultipleException;
import com.hr.exception.SueldosIncorrectosException;
import com.hr.exception.TrabajoDuplicadoException;
import com.hr.exception.TrabajoNoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JobService {
    Page<Job> listar(Pageable pageable);

    Optional<Job> porId(String id) throws TrabajoNoEncontradoException;

    Job saveNuevo(Job job) throws MultipleException;

    Job saveEditar(Job job) throws MultipleException;

    void eliminar(String id) throws TrabajoNoEncontradoException;
}
