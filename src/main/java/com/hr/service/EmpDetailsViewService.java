package com.hr.service;

import com.hr.entity.EmpDetailsView;
import com.hr.exception.EmpleadoNoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EmpDetailsViewService {
    Page<EmpDetailsView> listar(Pageable pageable);

    Optional<EmpDetailsView> porId(Integer id) throws EmpleadoNoEncontradoException;
}
