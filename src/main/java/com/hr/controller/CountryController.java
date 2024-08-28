package com.hr.controller;

import com.hr.dto.CountryDTO;
import com.hr.exception.*;
import com.hr.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/countries")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar países", description = "Lista de todos los países")
    public Page<CountryDTO> listar(Pageable pageable) {
        return countryService.listar(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "País por ID", description = "Obtener los datos de un país dado su ID")
    public CountryDTO porId(@PathVariable String id) throws CountryNoEncontradoException {
        return countryService.porId(id).orElseThrow(() -> new CountryNoEncontradoException("No existe el país con id: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear país", description = "Crear un nuevo país")
    public CountryDTO save(@RequestBody CountryDTO country) throws MultipleException, CountryNoEncontradoException, RegionNoEncontradoException, NombrePaisNoEncontradoException {
        return countryService.save(country);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Editar país", description = "Editar un país existente")
    public CountryDTO update(@RequestBody CountryDTO country, @PathVariable String id) throws MultipleException, CountryNoEncontradoException, RegionNoEncontradoException, NombrePaisNoEncontradoException {
        if (countryService.porId(id).isEmpty()) {
            throw new CountryNoEncontradoException("No existe el país con id: " + id);
        } else {
            country.setCountryId(id);
        }
            return countryService.save(country);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar país", description = "Eliminar un país existente")
    public void eliminar(@PathVariable String id) throws CountryNoEncontradoException {
        countryService.eliminar(id);
    }
}
