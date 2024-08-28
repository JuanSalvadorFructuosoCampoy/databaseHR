package com.hr.service;

import com.hr.exception.*;
import com.hr.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

@Service
public interface EmployeeService {
    Page<EmployeeDTO> listar(Float minSalary, Date hireDate, Pageable pageable);

    Optional<EmployeeDTO> porId(int id) throws EmpleadoNoEncontradoException;

    EmployeeDTO save(EmployeeDTO employeeDTO) throws MultipleException, EmailDuplicadoException, ParseException;

    void eliminar(int id) throws EmpleadoNoEncontradoException;

}
