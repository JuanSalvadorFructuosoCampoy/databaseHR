package com.hr.controller;

import com.hr.entity.EmpDetailsView;
import com.hr.exception.EmpleadoNoEncontradoException;
import com.hr.service.EmpDetailsViewService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empDetailsView")
public class EmpDetailsViewController {

    private final EmpDetailsViewService empDetailsViewService;

    @Autowired
    public EmpDetailsViewController(EmpDetailsViewService empDetailsViewService) {
        this.empDetailsViewService = empDetailsViewService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar detalle empleados", description = "Lista de los detalles de todos los empleados")
    public Page<EmpDetailsView> listar(Pageable pageable) {
        return empDetailsViewService.listar(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Ver detalle empleado por ID", description = "Muestra el detalle de un empleado por su ID")
    public EmpDetailsView porId(@PathVariable Integer id) throws EmpleadoNoEncontradoException {
        return empDetailsViewService.porId(id).orElseThrow(() -> new EmpleadoNoEncontradoException("No existe el empleado con id: " + id));
    }
}
