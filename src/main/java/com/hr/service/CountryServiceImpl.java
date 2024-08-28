package com.hr.service;

import com.hr.dto.CountryDTO;
import com.hr.entity.Country;
import com.hr.exception.*;
import com.hr.repository.CountryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ModelMapper countryModelMapper;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper countryModelMapper) {
        this.countryRepository = countryRepository;
        this.countryModelMapper = countryModelMapper;
    }

    @Override
    public Page<CountryDTO> listar(Pageable pageable) {
        return countryRepository.findAll(pageable).map(country -> countryModelMapper.map(country, CountryDTO.class));
    }

    @Override
    public Optional<CountryDTO> porId(String id) throws CountryNoEncontradoException {
        Optional<Country> country = countryRepository.findById(id);
        if (country.isEmpty()) {
            throw new CountryNoEncontradoException("No se ha encontrado el país con ID: " + id);
        }
        return country.map(country1 -> countryModelMapper.map(country1, CountryDTO.class));
    }

    @Override
    public CountryDTO save(CountryDTO countryDTO) throws MultipleException, NombrePaisNoEncontradoException {
        MultipleException multipleException = new MultipleException();
        Country pais = new Country();
        if(countryDTO.getCountryName().trim().isEmpty()) {
            multipleException.addException(new NombrePaisNoEncontradoException("El nombre del país no puede estar vacío"));
        }

        if (countryDTO.getCountryId() != null) {
            Optional<Country> paisExistente = countryRepository.findById(countryDTO.getCountryId());
            if (paisExistente.isPresent()) {
                pais = countryModelMapper.map(countryDTO, Country.class);
                pais.setCountryId(paisExistente.get().getCountryId());
            } else {
                multipleException.addException(new CountryNoEncontradoException("No se ha encontrado el país con ID: " + countryDTO.getCountryId()));
            }

        } else {
            pais = countryModelMapper.map(countryDTO, Country.class);
        }
        if (countryRepository.findByRegionName(countryDTO.getRegion()) != null) {
            pais.setRegion(countryRepository.findByRegionName(countryDTO.getRegion().trim()));
        } else {
            multipleException.addException(new RegionNoEncontradoException("No se ha encontrado la región con nombre: " + countryDTO.getRegion().trim()));
        }
        if (multipleException.hasExceptions()) {
            throw multipleException;
        }
        pais.setCountryName(countryDTO.getCountryName());
        if(pais.getCountryId() == null) {
            if(countryRepository.findById(pais.getCountryName().substring(0, 2).toUpperCase()).isPresent())
                pais.setCountryId(pais.getCountryName().substring(0, 1).toUpperCase().concat(pais.getCountryName().substring(2, 3).toUpperCase()));
            else
                pais.setCountryId(pais.getCountryName().substring(0, 2).toUpperCase());
        }

        countryRepository.save(pais);
        return countryModelMapper.map(pais, CountryDTO.class);
    }

        @Override
        public void eliminar (String id) throws CountryNoEncontradoException {
            if (countryRepository.findById(id).isEmpty()) {
                throw new CountryNoEncontradoException("No se ha encontrado el país con ID: " + id);
            }
            countryRepository.deleteById(id);
        }
    }
