package com.hr.controller;

import com.hr.dto.LocationDTO;
import com.hr.exception.CountryNoEncontradoException;
import com.hr.exception.LocationNoEncontradoException;
import com.hr.exception.MultipleException;
import com.hr.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar localizaciones", description = "Lista todas las localizaciones")
    public Page<LocationDTO> listar(Pageable pageable) {
        return locationService.listar(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Localización por ID", description = "Obtener los datos de una localización dado su ID")
    public LocationDTO porId(@PathVariable Integer id) throws LocationNoEncontradoException {
        return locationService.porId(id).orElseThrow(() -> new LocationNoEncontradoException("No existe la localización con id: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear localización", description = "Crea una nueva localización")
    public LocationDTO save(@RequestBody LocationDTO locationDTO) throws MultipleException, CountryNoEncontradoException, LocationNoEncontradoException {
        return locationService.save(locationDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Editar localización", description = "Editar una localización existente")
    public LocationDTO update(@RequestBody LocationDTO locationDTO, @PathVariable Integer id) throws MultipleException, LocationNoEncontradoException, CountryNoEncontradoException {
        if (locationService.porId(id).isEmpty()) {
            throw new LocationNoEncontradoException("No existe la localización con id: " + id);
        } else {
            locationDTO.setLocationId(id);
        }
        return locationService.save(locationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar localización", description = "Eliminar una localización existente")
    public void delete(@PathVariable Integer id) throws LocationNoEncontradoException {
        locationService.eliminar(id);
    }
}
