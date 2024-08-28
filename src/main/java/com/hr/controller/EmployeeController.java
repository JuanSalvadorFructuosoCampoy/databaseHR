package com.hr.controller;

import com.hr.exception.*;
import com.hr.dto.EmployeeDTO;
import com.hr.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar empleados", description = "Lista todos los empleados")
    public Page<EmployeeDTO> listar(@RequestParam(required = false) Float minSalary, @RequestParam(required = false) Date hireDate, Pageable pageable) {
        return employeeService.listar(minSalary, hireDate, pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener un empleado por ID", description = "Obtiene un empleado por ID")
    public EmployeeDTO porId(@PathVariable int id) throws EmpleadoNoEncontradoException{
        return employeeService.porId(id).orElseThrow(() -> new EmpleadoNoEncontradoException("No existe el empleado con id: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un empleado", description = "Crea un empleado nuevo")
    public EmployeeDTO crear(EmployeeDTO employeeDTO) throws MultipleException, EmailDuplicadoException, ParseException {
        return employeeService.save(employeeDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Editar un empleado por ID", description = "Edita un empleado por ID")
    public EmployeeDTO editar(@PathVariable Integer id, EmployeeDTO employeeDTO) throws EmpleadoNoEncontradoException, MultipleException, EmailDuplicadoException, ParseException {
        if(employeeService.porId(id).isEmpty()) {
            throw new EmpleadoNoEncontradoException("No existe el empleado con id: " + id);
        }else{
            employeeDTO.setEmployeeId(id);
        }
        return employeeService.save(employeeDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un empleado por ID", description = "Elimina un empleado por ID")
    public void eliminar(@PathVariable int id) throws EmpleadoNoEncontradoException{
        employeeService.eliminar(id);
    }
}