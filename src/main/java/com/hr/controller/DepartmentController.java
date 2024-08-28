package com.hr.controller;
import com.hr.dto.DepartmentDTO;
import com.hr.dto.DepartmentDTOGet;
import com.hr.exception.DepartamentoNoEncontradoException;
import com.hr.exception.MultipleException;
import com.hr.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar departamentos", description = "Lista de todos los departamentos")
    public Page<DepartmentDTOGet> listar(Pageable pageable) {
        return departmentService.listar(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener un departamento por ID", description = "Obtiene un departamento por ID")
    public DepartmentDTOGet porId(@PathVariable Integer id) throws DepartamentoNoEncontradoException {
        return departmentService.porId(id).orElseThrow(() -> new DepartamentoNoEncontradoException("No existe el departamento con id: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un departamento", description = "Crea un departamento nuevo")
    public DepartmentDTO save(@RequestBody DepartmentDTO departmentDto) throws MultipleException {
        return departmentService.save(departmentDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Actualizar un departamento", description = "Actualiza un departamento existente")
    public DepartmentDTO update(@PathVariable Integer id, @RequestBody DepartmentDTO departmentDto) throws MultipleException, DepartamentoNoEncontradoException {
        if(departmentService.porId(id).isEmpty()) {
            throw new DepartamentoNoEncontradoException("No existe el departamento con id: " + id);
        } else {
            departmentDto.setDepartmentId(id);
        }
        return departmentService.save(departmentDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un departamento", description = "Elimina un departamento existente")
    public void delete(@PathVariable Integer id) throws DepartamentoNoEncontradoException {
        if(departmentService.porId(id).isEmpty()) {
            throw new DepartamentoNoEncontradoException("No existe el departamento con id: " + id);
        } else {
            departmentService.eliminar(id);
        }
    }
}
