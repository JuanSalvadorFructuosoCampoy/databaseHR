package com.hr.controller;

import com.hr.dto.JobHistoryDTO;
import com.hr.service.JobHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-history")
public class JobHistoryController {

    private final JobHistoryService jobHistoryService;

    @Autowired
    public JobHistoryController(JobHistoryService jobHistoryService) {
        this.jobHistoryService = jobHistoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary="Listar el historial de trabajos", description="Lista todo el historial de trabajos de los empleados que han cambiado de empleo dentro de la empresa")
    public Page<JobHistoryDTO> listar(Pageable pageable) {
        return jobHistoryService.listar(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary="Listar el historial de trabajos por ID de empleado", description="Lista todo el historial de trabajos de un empleado que ha cambiado de empleo dentro de la empresa")
    public Page<JobHistoryDTO> listarPorId(@PathVariable Integer id, Pageable pageable) {
        return jobHistoryService.listarPorId(id, pageable);
    }

}
