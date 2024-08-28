package com.hr.service;

import com.hr.dto.CountryDTO;
import com.hr.exception.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CountryService{
    Page<CountryDTO> listar(Pageable pageable);

    Optional<CountryDTO> porId(String id) throws CountryNoEncontradoException;

    CountryDTO save(CountryDTO region) throws CountryNoEncontradoException, RegionNoEncontradoException, MultipleException, NombrePaisNoEncontradoException;

    void eliminar(String id) throws CountryNoEncontradoException;
}
