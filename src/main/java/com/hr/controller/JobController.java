package com.hr.controller;

import com.hr.entity.Job;
import com.hr.exception.MultipleException;
import com.hr.exception.SueldosIncorrectosException;
import com.hr.exception.TrabajoDuplicadoException;
import com.hr.exception.TrabajoNoEncontradoException;
import com.hr.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar trabajos", description = "Lista todos los trabajos")
    public Page<Job> listar(Pageable pageable) {
        return jobService.listar(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Trabajo por ID", description = "Obtener los datos de un trabajo por su ID")
    public Job porId(@PathVariable String id) throws TrabajoNoEncontradoException {
        return jobService.porId(id).orElseThrow(() -> new TrabajoNoEncontradoException("No existe el trabajo con id: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear trabajo", description = "Crear un nuevo trabajo")
    public Job save(@RequestBody Job job) throws TrabajoDuplicadoException, SueldosIncorrectosException, MultipleException {
        return jobService.saveNuevo(job);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Editar trabajo", description = "Editar un trabajo existente")
    public Job update(@RequestBody Job job, @PathVariable String id) throws MultipleException, TrabajoNoEncontradoException {
        if (jobService.porId(id).isEmpty()) {
            throw new TrabajoNoEncontradoException("No existe el trabajo con id: " + id);
        } else {
            job.setJobId(id);
        }
        return jobService.saveEditar(job);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar trabajo", description = "Eliminar un trabajo existente")
    public void delete(@PathVariable String id) throws TrabajoNoEncontradoException {
        jobService.porId(id).orElseThrow(() -> new TrabajoNoEncontradoException("No existe el trabajo con id: " + id));
        jobService.eliminar(id);
    }
}

