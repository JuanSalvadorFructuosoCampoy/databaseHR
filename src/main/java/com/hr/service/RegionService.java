package com.hr.service;

import com.hr.entity.Region;
import com.hr.dto.RegionDTO;
import com.hr.exception.RegionDuplicadoException;
import com.hr.exception.RegionNoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RegionService {
    Page<RegionDTO> listar(Pageable pageable);

    Optional<RegionDTO> porId(Integer id) throws RegionNoEncontradoException;

    Region save(Region region) throws RegionDuplicadoException, RegionNoEncontradoException;

    void eliminar(Integer id) throws RegionNoEncontradoException;
}
