package com.hr.service;

import com.hr.dto.DepartmentDTO;
import com.hr.dto.DepartmentDTOGet;
import com.hr.entity.Department;
import com.hr.exception.DepartamentoNoEncontradoException;
import com.hr.exception.MultipleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DepartmentService {
    Page<DepartmentDTOGet> listar(Pageable pageable);

    Optional<DepartmentDTOGet> porId(Integer id) throws DepartamentoNoEncontradoException;

    DepartmentDTO save(DepartmentDTO departmentDto) throws MultipleException;

    void eliminar(Integer id) throws DepartamentoNoEncontradoException;
}
