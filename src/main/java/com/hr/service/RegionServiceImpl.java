package com.hr.service;

import com.hr.entity.Region;
import com.hr.dto.RegionDTO;
import com.hr.exception.RegionDuplicadoException;
import com.hr.exception.RegionNoEncontradoException;
import com.hr.repository.RegionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegionServiceImpl implements RegionService{
    private final ModelMapper regionModelMapper;
    private final RegionRepository regionRepository;

    @Autowired
    public RegionServiceImpl(RegionRepository regionRepository, ModelMapper regionModelMapper) {
        this.regionRepository = regionRepository;
        this.regionModelMapper = regionModelMapper;
    }

    @Override
    public Page<RegionDTO> listar(Pageable pageable) {
        return regionRepository.findAll(pageable).map(country -> regionModelMapper.map(country, RegionDTO.class));
    }
    @Override
    public Optional<RegionDTO> porId(Integer id) throws RegionNoEncontradoException {
       Optional<Region> region = regionRepository.findById(id);
       if(region.isEmpty()) {
              throw new RegionNoEncontradoException("No se ha encontrado la región con id: " + id);
         } else {
           return region.map(region1 -> regionModelMapper.map(region1, RegionDTO.class));
       }
    }

    @Override
    public Region save(Region region) throws RegionDuplicadoException, RegionNoEncontradoException {
         if(region.getRegionId() == null) {
            //La base de datos no tiene Autoincrement para regions, por lo que creamos el id manualmente
            Integer idNuevo = (regionRepository.findMaxRegionId()) + 1;
            region.setRegionId(idNuevo);
        }
            return regionRepository.save(region);
        }

    @Override
    public void eliminar(Integer id) throws RegionNoEncontradoException {
        if(regionRepository.existsById(id)) {
            regionRepository.deleteById(id);
        } else {
            throw new RegionNoEncontradoException("No se ha encontrado la región con id: " + id);
        }
    }
}
