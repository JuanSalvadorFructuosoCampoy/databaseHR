package com.hr.controller;

import com.hr.entity.Region;
import com.hr.dto.RegionDTO;
import com.hr.exception.RegionDuplicadoException;
import com.hr.exception.RegionNoEncontradoException;
import com.hr.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/regions")
public class RegionController {

    private final RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar regiones", description = "Lista todas las regiones")
    public Page<RegionDTO> listar(Pageable pageable) {
        return regionService.listar(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener region por ID", description = "Obtener los datos de una región dado su ID")
    public RegionDTO porId(@PathVariable Integer id) throws RegionNoEncontradoException {
        return regionService.porId(id).orElseThrow(() -> new RegionNoEncontradoException("No existe la región con id: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear región", description = "Crea una nueva región")
    public Region save(@RequestBody Region region) throws RegionDuplicadoException, RegionNoEncontradoException {
        return regionService.save(region);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Actualizar región", description = "Actualiza una región")
    public Region update(@RequestBody Region region, @PathVariable Integer id) throws RegionDuplicadoException, RegionNoEncontradoException {
        if (regionService.porId(id).isEmpty()) {
            throw new RegionNoEncontradoException("No existe la región con id: " + id);
        } else {
            region.setRegionId(id);
            return regionService.save(region);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar región", description = "Elimina una región")
    public void eliminar(@PathVariable Integer id) throws RegionNoEncontradoException {
        regionService.eliminar(id);
    }
}
