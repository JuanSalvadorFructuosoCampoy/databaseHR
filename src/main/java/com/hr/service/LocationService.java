package com.hr.service;

import com.hr.dto.LocationDTO;
import com.hr.exception.CountryNoEncontradoException;
import com.hr.exception.LocationNoEncontradoException;
import com.hr.exception.MultipleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LocationService {
    Page<LocationDTO> listar(Pageable pageable);

    Optional<LocationDTO> porId(Integer id) throws LocationNoEncontradoException;

    LocationDTO save(LocationDTO locationDTO) throws LocationNoEncontradoException, CountryNoEncontradoException, MultipleException;

    void eliminar(Integer id) throws LocationNoEncontradoException;
}
