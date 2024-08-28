package com.hr.service;

import com.hr.dto.LocationDTO;
import com.hr.entity.Location;
import com.hr.exception.*;
import com.hr.repository.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final ModelMapper locationModelMapper;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, ModelMapper locationModelMapper) {
        this.locationRepository = locationRepository;
        this.locationModelMapper = locationModelMapper;
    }

    @Override
    public Page<LocationDTO> listar(Pageable pageable) {
        return locationRepository.findAll(pageable).map(location -> locationModelMapper.map(location, LocationDTO.class));
    }

    @Override
    public Optional<LocationDTO> porId(Integer id) throws LocationNoEncontradoException {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isEmpty()) {
            throw new LocationNoEncontradoException("No existe la localización con id: " + id);
        }
        return location.map(location1 -> locationModelMapper.map(location1, LocationDTO.class));
    }

    @Override
    public LocationDTO save(LocationDTO locationDTO) throws LocationNoEncontradoException, CountryNoEncontradoException, MultipleException {
        MultipleException multipleException = new MultipleException();
        Location location = new Location();
        if (locationDTO.getStreetAddress().trim().isEmpty()) {
            multipleException.addException(new LocationNoEncontradoException("La dirección no puede estar vacía"));
        }
        if (locationDTO.getPostalCode().trim().isEmpty()) {
            multipleException.addException(new PostalCodeNoEncontradoException("El código postal no puede estar vacío"));
        }
        if(locationDTO.getCity().trim().isEmpty()) {
            multipleException.addException(new CityNoEncontradoException("La ciudad no puede estar vacía"));
        }
        if(locationDTO.getStateProvince().trim().isEmpty()) {
            multipleException.addException(new StateProvinceNoEncontradoException("La provincia no puede estar vacía"));
        }
        if (locationDTO.getLocationId() != null) {
            Optional<Location> locationExistente = locationRepository.findById(locationDTO.getLocationId());
            if (locationExistente.isPresent()) {
                location = locationModelMapper.map(locationDTO, Location.class);
                location.setLocationId(locationExistente.get().getLocationId());
            } else {
                multipleException.addException(new LocationNoEncontradoException("No se ha encontrado la localización con ID: " + locationDTO.getLocationId()));
            }

        } else {
            location = locationModelMapper.map(locationDTO, Location.class);
        }
        if (locationRepository.findByCountryName(locationDTO.getCountryName()) != null) {
            location.setCountry(locationRepository.findByCountryName(locationDTO.getCountryName()));
        } else {
            multipleException.addException(new CountryNoEncontradoException("No se ha encontrado el país con nombre: " + locationDTO.getCountryName()));
        }

        if (multipleException.hasExceptions()) {
            throw multipleException;
        }
        location.setStreetAddress(locationDTO.getStreetAddress());
        location.setPostalCode(locationDTO.getPostalCode());
        location.setCity(locationDTO.getCity());
        location.setStateProvince(locationDTO.getStateProvince());
        locationRepository.save(location);
        return locationModelMapper.map(location, LocationDTO.class);
    }

    @Override
    public void eliminar(Integer id) throws LocationNoEncontradoException {
        if (locationRepository.findById(id).isEmpty()) {
            throw new LocationNoEncontradoException("No se ha encontrado la localización con ID: " + id);
        }
        locationRepository.deleteById(id);
    }


}
